package com.bot4s.telegram.api.declarative

import cats.syntax.flatMap._
import cats.syntax.functor._
import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.methods.SendMessage
import com.bot4s.telegram.models.{Message, MessageEntityType, ReplyMarkup}

import java.util.concurrent.ConcurrentHashMap

trait Deferred[F[_], A] {

  def complete(a: A): F[Unit]

  def get: F[A]
}

object Chats {
  sealed trait ExitCase
  case object Canceled extends ExitCase
  case class Error(e: Throwable) extends ExitCase
}

import Chats._

trait TelegramChat[F[_]] {

  def write(
    text: String,
    parseMode: Option[ParseMode] = None,
    disableWebPagePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    replyMarkup: Option[ReplyMarkup] = None
  ): F[Message]

  def read(): F[Message]

  def readCase(): F[Either[ExitCase, Message]]
}

trait Chats[F[_]] extends Messages[F] {

  private val waitingQueue = new ConcurrentHashMap[Long, Deferred[F, Either[ExitCase, Message]]]()

  def createChat(chatId: Long) =
    new TelegramChat[F] {
      override def write(
        text: String,
        parseMode: Option[ParseMode] = None,
        disableWebPagePreview: Option[Boolean] = None,
        disableNotification: Option[Boolean] = None,
        replyToMessageId: Option[Int] = None,
        replyMarkup: Option[ReplyMarkup] = None
      ): F[Message] =
        request(SendMessage(chatId, text, parseMode, disableWebPagePreview, disableNotification,
          replyToMessageId, replyMarkup))

      override def read(): F[Message] =
        readCase().flatMap {
          case Right(result) => monad.pure(result)
          case Left(Canceled) => monad.raiseError(new Exception("canceled"))
          case Left(Error(e)) => monad.raiseError(e)
        }

      override def readCase(): F[Either[ExitCase, Message]] =
        for {
          dfd <- makeDeferred[Either[ExitCase, Message]]
          maybePrevDfd <- putDeferredInQueue(chatId, dfd)
          _ <- maybePrevDfd.fold(monad.unit)(_.complete(Left(Canceled)))
          result <- dfd.get
        } yield result
    }

  def makeDeferred[A]: F[Deferred[F, A]]

  def delay[A](thunk: => A): F[A]

  override def receiveMessage(msg: Message): F[Unit] =
    if (msg.entities.getOrElse(Seq.empty).exists(_.`type` == MessageEntityType.BotCommand)) {
      super.receiveMessage(msg)
    } else {
      for {
        maybeDfd <- extractFromQueue(msg.source)
        _ <- maybeDfd.fold(monad.unit)(_.complete(Right(msg)))
        _ <- super.receiveMessage(msg)
      } yield ()
    }

  def putDeferredInQueue(chatId: Long, dfd: Deferred[F, Either[ExitCase, Message]]):
      F[Option[Deferred[F, Either[ExitCase, Message]]]] =
    delay(Option(waitingQueue.put(chatId, dfd)))

  def extractFromQueue(chatId: Long): F[Option[Deferred[F, Either[ExitCase, Message]]]] =
    delay(Option(waitingQueue.remove(chatId)))
}
