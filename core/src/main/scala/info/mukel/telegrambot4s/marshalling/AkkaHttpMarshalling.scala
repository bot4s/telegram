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
        val values = fields.map { f =>
          f.setAccessible(true)
          (f.getName(), f.get(request))
        }

        def unwrap(obj: Any): Any = obj match {
          case Some(inner) => unwrap(inner)
          case _ => obj
        }

        val params = values
          .map {
            case (name, value) => (camelToUnderscores(name), unwrap(value))
          }
          .filterNot(_._2 == None)

        val parts = params map {
          case (key, v) => v match {

            // Handle files
            case InputFile.FileId(fileId) =>
              Multipart.FormData.BodyPart(key, HttpEntity(fileId))

            case InputFile.Path(path) =>
              Multipart.FormData.BodyPart.fromPath(key, MediaTypes.`application/octet-stream`, path)

            case InputFile.ByteString(filename, bytes) =>
              Multipart.FormData.BodyPart(key, HttpEntity(MediaTypes.`application/octet-stream`, bytes),
                Map("filename" -> filename))

            case InputFile.Contents(filename, contents) =>
              Multipart.FormData.BodyPart(key, HttpEntity(ContentTypes.`application/octet-stream`, contents),
                Map("filename" -> filename))

            case other =>

              def unquote(s: String): String = {
                val quote = "\""
                s.stripSuffix(quote).stripPrefix(quote)
              }
              // Strings should be sent unquoted.
              Multipart.FormData.BodyPart(key, HttpEntity(unquote(toJson(other))))
          }
        }

        Marshalling.Opaque(() => Multipart.FormData(parts: _*).toEntity())
      }
    }
  }
}
