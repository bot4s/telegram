package info.mukel.telegrambot4s

import info.mukel.telegrambot4s.models.{InlineKeyboardButton, KeyboardButton}

import scala.language.implicitConversions

/**
  * Useful/scary implicits to reduce boilerplate.
  *
  * Note that implicit Option conversion can have unexpected side effects,
  * use wisely; at your own risk.
  */
object Implicits {

  implicit def toSeqSeqKeyboardButton(kb: KeyboardButton): Seq[Seq[KeyboardButton]] =
    Seq(Seq(kb))

  implicit def toSeqSeqKeyboardButtonSeq(skb: Seq[KeyboardButton]): Seq[Seq[KeyboardButton]] =
    skb.map(Seq(_))

  implicit def toSeqSeqInlineKeyboardButton(ikb: InlineKeyboardButton): Seq[Seq[InlineKeyboardButton]] =
    Seq(Seq(ikb))

  implicit def toSeqSeqInlineKeyboardButtonSeq(sikb: Seq[InlineKeyboardButton]): Seq[Seq[InlineKeyboardButton]] =
    sikb.map(Seq(_))

  implicit def toOption[T](v: T) : Option[T] = Option(v)

  implicit class MarkdownString(s: String) {
    def bold = s"*$s*"
    def italic = s"_${s}_"
    def urlWithAlt(alt: String) = s"[$alt]($s)"
    def altWithUrl(url: String) = s"[$s]($url)"
    def inlineCode = s"`$s`"
    def blockCode(language: String = "text") = s"```$language\n$s\n```"
  }
}
