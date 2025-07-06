package com.bot4s.telegram.marshalling

import java.util.NoSuchElementException

import com.bot4s.telegram.methods.ChatAction.ChatAction
import com.bot4s.telegram.methods.PollType.PollType
import com.bot4s.telegram.models._
import com.bot4s.telegram.methods._
import com.bot4s.telegram.methods.{ ChatAction, PollType, Response }
import com.bot4s.telegram.models.ChatType.ChatType
import com.bot4s.telegram.models.CountryCode.CountryCode
import com.bot4s.telegram.models.Currency.Currency
import com.bot4s.telegram.models.MaskPositionType.MaskPositionType
import com.bot4s.telegram.models.MemberStatus.MemberStatus
import com.bot4s.telegram.models.BotCommandScope.BotCommandScope
import com.bot4s.telegram.models.MessageEntityType.MessageEntityType
import com.bot4s.telegram.models.StickerType.StickerType
import com.bot4s.telegram.models.StickerFormat.StickerFormat
import UpdateType.UpdateType
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import com.typesafe.scalalogging.StrictLogging

/**
 * Circe marshalling borrowed/inspired from [[https://github.com/nikdon/telepooz]]
 */
trait CirceDecoders extends StrictLogging {

  implicit def responseDecoder[T: Decoder]: Decoder[Response[T]] = deriveDecoder[Response[T]]

  implicit def eitherDecoder[A, B](implicit decA: Decoder[A], decB: Decoder[B]): Decoder[Either[A, B]] = {
    val l: Decoder[Either[A, B]] = decA.map(Left.apply)
    val r: Decoder[Either[A, B]] = decB.map(Right.apply)
    l or r
  }

  implicit def eitherMessageBooleanDecoder: Decoder[Either[Boolean, Message]] = eitherDecoder[Boolean, Message]

  implicit def eitherBooleanMessageDecoder: Decoder[Either[Message, Boolean]] = eitherDecoder[Message, Boolean]

  implicit def seqGameHighScoreDecoder: Decoder[Seq[GameHighScore]] = Decoder.decodeSeq[GameHighScore]

  implicit def seqParsedUpdateDecoder: Decoder[Seq[ParsedUpdate]] = Decoder.decodeSeq[ParsedUpdate]

  implicit def seqChatMemberDecoder: Decoder[Seq[ChatMember]] = Decoder.decodeSeq[ChatMember]

  implicit def listBotCommandDecoder: Decoder[List[BotCommand]] = Decoder.decodeList[BotCommand]

  implicit def listStickerDecoder: Decoder[List[Sticker]] = Decoder.decodeList[Sticker]

  implicit def arrayMessageDecoder: Decoder[Array[Message]] = Decoder.decodeArray[Message]

}

object CirceDecoders extends CirceDecoders
