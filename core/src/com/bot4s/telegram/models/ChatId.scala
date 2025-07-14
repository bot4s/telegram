package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.syntax._

/**
 * Represents either a chat or channel.
 */
sealed trait ChatId {
  def isChannel: Boolean
  def isChat: Boolean = !isChannel
  def toEither: Either[Long, String]
}

object ChatId {
  implicit def fromChat[T](id: Long): ChatId      = ChatId(id)
  implicit def fromChannel[T](id: String): ChatId = ChatId(id)

  final case class Chat(id: Long) extends ChatId {
    override def isChannel: Boolean             = false
    override def toEither: Either[Long, String] = Left(id)
  }

  final case class Channel(id: String) extends ChatId {
    override def isChannel: Boolean             = true
    override def toEither: Either[Long, String] = Right(id)
  }

  def apply(chat: Long): ChatId      = Chat(chat)
  def apply(channel: String): ChatId = Channel(channel)

  implicit val circeDecoder: Decoder[ChatId] =
    Decoder[String].map(ChatId.Channel.apply) or Decoder[Long].map(ChatId.Chat.apply)

  implicit val circeEncoder: Encoder[ChatId] = Encoder.instance {
    case ChatId.Chat(chat)       => chat.asJson
    case ChatId.Channel(channel) => channel.asJson
  }
}
