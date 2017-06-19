package info.mukel.telegrambot4s.marshalling

import akka.http.scaladsl.marshalling.{Marshaller, Marshalling, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, MediaTypes, Multipart}
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import com.typesafe.scalalogging.StrictLogging
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, Extraction, Formats, NoTypeHints}

/**
  * De/serialization support for JSON and multipart API requests.
  */
object HttpMarshalling extends StrictLogging {

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
    new EnumNameSerializer(MessageEntityType)
  )

  def toJson[T](t: T): String = compact(render(Extraction.decompose(t).underscoreKeys))

  def fromJson[T: Manifest](json: String): T = parse(json).camelizeKeys.extract[T]

  implicit def camelCaseJsonUnmarshaller[T : Manifest]: FromEntityUnmarshaller[T] = {
    Unmarshaller
      .stringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .map(fromJson[T])
  }

  private def camelToUnderscores(name: String): String = "[A-Z\\d]".r.replaceAllIn(name, { m =>
    "_" + m.group(0).toLowerCase()
  })

  implicit def underscore_case_marshaller[T]: ToEntityMarshaller[ApiRequest[T]] = {

    Marshaller.strict {
      // JSON-only request
      case request: ApiRequestJson[T] =>
        Marshalling.Opaque(() => HttpEntity(ContentTypes.`application/json`, toJson[ApiRequestJson[_]](request)))

      // Request with file payload
      case request: ApiRequestMultipart[T] => {

        def unwrap(f: Any): Any = f match {
          case Some(x)  => unwrap(x)
          case Left(x)  => unwrap(x)
          case Right(x) => unwrap(x)
          case _        => f
        }

        val fields = request.getClass.getDeclaredFields
        val values = request.productIterator
        val fieldNames = fields map (_.getName)

        val pairs = fieldNames
          .zip(values.toSeq)
          .map {
            case (k, v) => (camelToUnderscores(k), unwrap(v))
          }

        val params = pairs.filterNot(_._2 == None)

        val parts = params map {
          case (k, v) => v match {
            case primitive @ (_: Int | _: Long | _: Float | _: Double | _: Boolean | _: String) =>
              Multipart.FormData.BodyPart(k, HttpEntity(primitive.toString))

            // Handle files
            case InputFile.FileId(fileId) =>
              Multipart.FormData.BodyPart(k, HttpEntity(fileId))

            case InputFile.Path(path) =>
                Multipart.FormData.BodyPart.fromPath(k, MediaTypes.`application/octet-stream`, path)

            case InputFile.ByteString(filename, bytes) =>
                Multipart.FormData.BodyPart(k, HttpEntity(MediaTypes.`application/octet-stream`, bytes),
                  Map("filename" -> filename))

            case InputFile.Contents(filename, contents) =>
                Multipart.FormData.BodyPart(k, HttpEntity(ContentTypes.`application/octet-stream`, contents),
                  Map("filename" -> filename))

            case rm : ReplyMarkup =>
              Multipart.FormData.BodyPart(k, HttpEntity(toJson(rm)))

            case other =>
              logger.error(s"Unexpected value in multipart request: ($k -> $other)")
              Multipart.FormData.BodyPart(k, HttpEntity(other.toString))
          }
        }

        Marshalling.Opaque(() => Multipart.FormData(parts: _*).toEntity())
      }
    }
  }
}
