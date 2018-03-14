package info.mukel.telegrambot4s.marshalling

import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models.{ChatId, _}
import org.json4s.JsonAST.{JInt, JString}
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods._
import org.json4s.{CustomSerializer, DefaultFormats, Extraction, Formats, NoTypeHints}

/**
  * De/serialization support for JSON with transparent camelCase <-> snake_case
  * conversion.
  */
trait JsonMarshallers {

  private object ChatIdSerializer extends CustomSerializer[ChatId](format => ( {
    case JString(channel) => ChatId(channel)
    case JInt(chat) => ChatId(chat.toLong)
  }, {
    case ChatId.Channel(channel) => JString(channel)
    case ChatId.Chat(chat) => JInt(chat)
  }
  ))

  implicit val formats: Formats = (
    new Formats {
      val dateFormat = DefaultFormats.lossless.dateFormat
      override val typeHints = NoTypeHints

      // Throws MappingException if Option[_] cannot be parsed.
      override def strictOptionParsing: Boolean = true
    } +
      new EnumNameSerializer(ChatAction) +
      new EnumNameSerializer(ParseMode) +
      new EnumNameSerializer(ChatType) +
      new EnumNameSerializer(Currency) +
      new EnumNameSerializer(CountryCode) +
      new EnumNameSerializer(UpdateType) +
      new EnumNameSerializer(MessageEntityType) +
      new EnumNameSerializer(MaskPositionType) +
      ChatIdSerializer
    )

  def toJson[T](t: T): String = compact(render(Extraction.decompose(t).underscoreKeys))

  def fromJson[T: Manifest](json: String): T = parse(json).camelizeKeys.extract[T]
}

object JsonMarshallers extends JsonMarshallers