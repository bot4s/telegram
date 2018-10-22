package info.mukel.telegrambot4s.marshalling

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models.{ChatId, _}
import org.json4s.JsonAST.{JInt, JString, JValue}
import org.json4s.ext._
import org.json4s.jackson.JsonMethods._
import org.json4s.reflect.TypeInfo
import org.json4s.{CustomSerializer, DefaultFormats, Extraction, Formats, MappingException, NoTypeHints, Serializer}

import scala.reflect.ClassTag

/**
  * De/serialization support for JSON with transparent camelCase <-> snake_case
  * conversion.
  */
trait JsonMarshallers {

  private val logger = Logger(getClass)

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
      new EnumNameWithFallbackSerializer(MessageEntityType, MessageEntityType.Unknown) +
      new EnumNameSerializer(MaskPositionType) +
      ChatIdSerializer
    )

  def toJson[T](t: T): String = compact(render(Extraction.decompose(t).underscoreKeys))

  def fromJson[T: Manifest](json: String): T = parse(json).camelizeKeys.extract[T]

  private class EnumNameWithFallbackSerializer[E <: Enumeration: ClassTag](enum: E, fallback: E#Value)
    extends Serializer[E#Value] {
    import org.json4s.JsonDSL._

    val EnumerationClass = classOf[E#Value]

    def deserialize(implicit format: Formats):
    PartialFunction[(TypeInfo, JValue), E#Value] = {
      case (_ @ TypeInfo(EnumerationClass, _), json) if (isValid(json)) => {
        json match {
          case JString(value) =>
            try {
              enum.withName(value)
            } catch {
              case e: NoSuchElementException =>
                logger.warn(s"Unexpected MessageEntityType: '$value', fallback to '${fallback.toString}'.")
                fallback
            }
          case value => throw new MappingException(s"Can't convert $value to $EnumerationClass")
        }
      }
    }

    private[this] def isValid(json: JValue) = json match {
      case JString(_) => true
      case _ => false
    }

    def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
      case i: E#Value if enum.values.exists(_ == i) => i.toString
    }
  }

}

object JsonMarshallers extends JsonMarshallers