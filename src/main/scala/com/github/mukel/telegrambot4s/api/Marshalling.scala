package com.github.mukel.telegrambot4s.api

import akka.http.scaladsl.marshalling.{Marshaller, MultipartMarshallers, _}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, MediaTypes, Multipart}
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import com.github.mukel.telegrambot4s.methods.{ApiRequestJson, ApiRequestMultipart, ChatAction, ParseMode}
import com.github.mukel.telegrambot4s.models.InputFile
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.{Extraction, Formats, NoTypeHints}

/** Simple de/serailization for JSON and multipart API requests
  */
trait Marshalling {
  implicit val formats = Serialization.formats(NoTypeHints) +
    new EnumNameSerializer(ChatAction) +
    new EnumNameSerializer(ParseMode)

  implicit def camelCaseJsonUnmarshaller[T: Manifest](implicit formats: Formats): FromEntityUnmarshaller[T] = {
    Unmarshaller
      .byteStringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .mapWithCharset((data, charset) => {
        val json = data.decodeString(charset.nioCharset.name)
        parse(json).camelizeKeys.extract[T]
      })
  }

  implicit def underscore_case_json_marshaller[T]: ToEntityMarshaller[ApiRequestJson[T]] = {
    Marshaller
      .StringMarshaller
      .wrap(MediaTypes.`application/json`) {
        t: ApiRequestJson[T] =>
          compact(render(Extraction.decompose(t).underscoreKeys))
      }
  }

  private def camelToUnderscores(name: String): String = "[A-Z\\d]".r.replaceAllIn(name, { m =>
    "_" + m.group(0).toLowerCase()
  })

  implicit def underscore_case_multipart_marshaller[T]: ToEntityMarshaller[ApiRequestMultipart[T]] = {
    import MultipartMarshallers._
    Marshaller.combined {
      case caseClass: Product => {

        val values = caseClass.productIterator.toArray
        val paramMap = caseClass.getClass.getDeclaredFields
          .map(field => camelToUnderscores(field.getName))
          .zip(values)
          .toMap

        def unwrap(value: Any): Any = value match {
          case Some(x) => unwrap(x) // Option
          case Left(x) => unwrap(x) // Either.Left
          case Right(x) => unwrap(x) // Either.Right
          case InputFile.FromFileId(x) => x // Put just the fileId
          case _ => value
          // TODO: Enums
        }

        val params = paramMap.mapValues(unwrap).filterNot(_ == None)

        val bodyParts = params map {
          case (key, value) => value match {
            case primitive@(_: Int | _: Long | _: Float | _: Double | _: Boolean | _: String) =>
              Multipart.FormData.BodyPart(key, HttpEntity(primitive.toString))

            // Handle files
            case _: InputFile => value match {
              case InputFile.FromFile(file) =>
                Multipart.FormData.BodyPart.fromFile(key, MediaTypes.`application/octet-stream`, file)

              case InputFile.FromSource(filename, contentLength, source) =>

                Multipart.FormData.BodyPart.apply(
                  key,
                  HttpEntity(ContentTypes.`application/octet-stream`, contentLength, source),
                  Map("filename" -> filename)
                )

              case InputFile.FromByteString(filename, bytes) =>
                Multipart.FormData.BodyPart.apply(
                  key,
                  HttpEntity(ContentTypes.`application/octet-stream`, bytes),
                  Map("filename" -> filename)
                )
              // Not tested
              case InputFile.FromContents(filename, content) =>
                Multipart.FormData.BodyPart.apply(
                  key,
                  HttpEntity(ContentTypes.`application/octet-stream`, content),
                  Map("filename" -> filename)
                )
            }

            // Fallback to JSON, probably a markup
            case other: AnyRef =>
              Multipart.FormData.BodyPart(key, HttpEntity(Serialization.write(other)))
          }
        }

        Multipart.FormData(bodyParts.toSeq: _*)
      }
    }
  }
}
