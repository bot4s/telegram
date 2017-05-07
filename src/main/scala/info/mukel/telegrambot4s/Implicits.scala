package info.mukel.telegrambot4s

import info.mukel.telegrambot4s.api.RequestHandler
import info.mukel.telegrambot4s.methods.ApiRequest
import info.mukel.telegrambot4s.models.{InlineKeyboardButton, KeyboardButton}

import scala.concurrent.Future
import scala.language.implicitConversions
import scala.util.Try

/**
  * Useful implicits to reduce boilerplate.
  */
object Implicits {
  implicit def toEitherLeft [L, R](l: L) : Either[L, R] = Left(l)
  implicit def toEitherRight[L, R](r: R) : Either[L, R] = Right(r)

  implicit def toOptionEitherLeft [L, R](l: L) : Option[Either[L, R]] = Option(Left(l))
  implicit def toOptionEitherRight[L, R](r: R) : Option[Either[L, R]] = Option(Right(r))

  implicit def toSeqSeqKeyboardButton(kb: KeyboardButton): Seq[Seq[KeyboardButton]] = Seq(Seq(kb))
  implicit def toSeqSeqKeyboardButtonSeq(skb: Seq[KeyboardButton]): Seq[Seq[KeyboardButton]] = Seq(skb)

  implicit def toSeqSeqInlineKeyboardButton(ikb: InlineKeyboardButton): Seq[Seq[InlineKeyboardButton]] = Seq(Seq(ikb))
  implicit def toSeqSeqInlineKeyboardButtonSeq(sikb: Seq[InlineKeyboardButton]): Seq[Seq[InlineKeyboardButton]] = sikb.map(Seq(_))

  implicit def toOption[T](v: T) : Option[T] = Option(v)

  implicit class MarkdownString(s: String) {
    def bold = s"*$s*"
    def italic = s"_${s}_"
    def urlWithAlt(alt: String) = s"[$alt]($s)"
    def altWithUrl(url: String) = s"[$s]($url)"
    def inlineCode = s"`$s`"
    def blockCode(language: String = "text") = s"```$language\n$s\n```"
  }

  implicit class OptionWrapper[T](opt: Option[T]) {
    def ?== (t: T): Boolean = opt.contains(t)
  }

  implicit class SuffixRequests[R: Manifest](r: ApiRequest[R]) {
    def request(implicit client: RequestHandler): Future[R] = client(r)
  }

  object Extractor {
    object Int { def unapply(s: String) = Try(s.toInt).toOption }
    object Long { def unapply(s: String) = Try(s.toLong).toOption }
    object Double { def unapply(s: String) = Try(s.toDouble).toOption }
    object Float { def unapply(s: String) = Try(s.toFloat).toOption }
  }
}
