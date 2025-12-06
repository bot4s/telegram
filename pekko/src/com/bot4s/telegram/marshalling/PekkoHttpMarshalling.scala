package com.bot4s.telegram.marshalling

import org.apache.pekko.http.scaladsl.marshalling.{ Marshaller, Marshalling, ToEntityMarshaller }
import org.apache.pekko.http.scaladsl.model._
import org.apache.pekko.http.scaladsl.unmarshalling.{ FromEntityUnmarshaller, Unmarshaller }
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.methods.{ JsonRequest, MultipartRequest, Request }
import com.bot4s.telegram.models.{ PekkoInputFile, InputFile }
import io.circe.{ Decoder, Encoder }

object PekkoHttpMarshalling {

  implicit def camelCaseJsonUnmarshaller[R](implicit decR: Decoder[R]): FromEntityUnmarshaller[R] =
    Unmarshaller.stringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .map(marshalling.fromJson[R])

  implicit def underscore_case_marshaller[T <: Request: Encoder]: ToEntityMarshaller[T] =
    Marshaller.strict { request =>
      request match {
        // JSON-only request
        case r: JsonRequest =>
          Marshalling.Opaque(() => HttpEntity(ContentTypes.`application/json`, marshalling.toJson(request)))

        // Request with multipart payload
        case r: MultipartRequest =>
          val files = r.getFiles
          val parts = files.map { case (camelKey, inputFile) =>
            val key = CaseConversions.snakenize(camelKey)
            inputFile match {
              case InputFile.FileId(id) => Multipart.FormData.BodyPart(key, HttpEntity(id))

              case InputFile.Contents(filename, contents) =>
                Multipart.FormData.BodyPart(
                  key,
                  HttpEntity(ContentTypes.`application/octet-stream`, contents),
                  Map("filename" -> filename)
                )

              case InputFile.Path(path) =>
                Multipart.FormData.BodyPart.fromPath(key, MediaTypes.`application/octet-stream`, path)

              case PekkoInputFile.ByteString(filename, bytes) =>
                Multipart.FormData.BodyPart(
                  key,
                  HttpEntity(MediaTypes.`application/octet-stream`, bytes),
                  Map("filename" -> filename)
                )

              case other =>
                throw new RuntimeException(s"InputFile $other not supported")
            }
          }

          val fields = io.circe.parser
            .parse(marshalling.toJson(request))
            .fold(
              throw _,
              _.asObject.map {
                _.toMap.mapValues { json =>
                  json.asString.getOrElse(marshalling.printer.print(json))
                }
              }
            )

          val params     = fields.getOrElse(Map()).toMap
          val paramParts = params.map { case (key, value) => Multipart.FormData.BodyPart(key, HttpEntity(value)) }

          Marshalling.Opaque(() => Multipart.FormData((parts ++ paramParts): _*).toEntity)
      }
    }
}
