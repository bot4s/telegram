package info.mukel.telegrambot4s.marshalling

import akka.http.scaladsl.marshalling.{Marshaller, Marshalling, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, MediaTypes, Multipart}
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import com.typesafe.scalalogging.LazyLogging
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models.{ChatId, _}
import JsonMarshallers._

object AkkaHttpMarshalling extends LazyLogging {

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
        Marshalling.Opaque(() => HttpEntity(ContentTypes.`application/json`, toJson[ApiRequestJson[T]](request)))

      // Request with file payload
      case request: ApiRequestMultipart[T] => {

        val fields = request.getClass.getDeclaredFields
        val values = request.productIterator
        val fieldNames = fields map (_.getName)

        val params = fieldNames
          .zip(values.toSeq)
          .map {
            // Unwrap options
            case (k, Some(v)) => (camelToUnderscores(k), v)
            case (k, v) => (camelToUnderscores(k), v)
          }
          .filterNot(_._2 == None)

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

            case ChatId.Channel(id) =>
              Multipart.FormData.BodyPart(k, HttpEntity(id))

            case ChatId.Chat(id) =>
              Multipart.FormData.BodyPart(k, HttpEntity(id.toString))

            case other =>
              logger.error(s"Unexpected value in multipart request: ($k -> $other)")
              Multipart.FormData.BodyPart(k, HttpEntity(toJson(other)))
          }
        }

        Marshalling.Opaque(() => Multipart.FormData(parts: _*).toEntity())
      }
    }
  }
}
