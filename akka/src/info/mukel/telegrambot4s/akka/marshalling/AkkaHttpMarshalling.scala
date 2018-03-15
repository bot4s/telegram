package info.mukel.telegrambot4s.akka.marshalling

import akka.http.scaladsl.marshalling.{Marshaller, Marshalling, ToEntityMarshaller}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, MediaTypes, Multipart}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import info.mukel.telegrambot4s.akka.models.AkkaInputFile
import info.mukel.telegrambot4s.marshalling.JsonMarshallers
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiRequestJson, ApiRequestMultipart}
import info.mukel.telegrambot4s.models.InputFile
;

object AkkaHttpMarshalling {

  import JsonMarshallers._

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

        def unwrap(obj: Any): Any = obj match {
          case Some(inner) => unwrap(inner)
          case _ => obj
        }

        val params = request.getClass.getDeclaredFields.map { f =>
          f.setAccessible(true)
          val name = f.getName()
          val value = f.get(request)
          (camelToUnderscores(name), unwrap(value))
        }.filterNot(_._2 == None)

        val parts = params.map {
          case (key, v) => v match {

            // Handle files
            case InputFile.FileId(fileId) =>
              Multipart.FormData.BodyPart(key, HttpEntity(fileId))

            case InputFile.Path(path) =>
              Multipart.FormData.BodyPart.fromPath(key, MediaTypes.`application/octet-stream`, path)

            case AkkaInputFile.ByteString(filename, bytes) =>
              Multipart.FormData.BodyPart(key, HttpEntity(MediaTypes.`application/octet-stream`, bytes),
                Map("filename" -> filename))

            case InputFile.Contents(filename, contents) =>
              Multipart.FormData.BodyPart(key, HttpEntity(ContentTypes.`application/octet-stream`, contents),
                Map("filename" -> filename))

            case _ : InputFile =>
              throw new UnsupportedOperationException("Akka marshaller client does not support this InputFile")


            // [Bug #49] JSON-serializing top level strings causes line ends to be sent as \n.
            // Top level parameters (non-JSON entities) must be passed as is (raw).
            // Note: This fixes String parameters, string-like fields e.g. chat_id and file ids should
            // not contain line breaks or awkward characters.
            case s : String =>
              Multipart.FormData.BodyPart(key, HttpEntity(s))

            case other =>
              def unquote(s: String): String = {
                val quote = "\""
                if (s.startsWith(quote) && s.endsWith(quote))
                  s.stripSuffix(quote).stripPrefix(quote)
                else
                  s
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
