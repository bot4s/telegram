package info.mukel.telegram.bots.v2.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.model.Multipart.FormData.BodyPart
import akka.http.scaladsl.model.{HttpEntity, _}
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer
import info.mukel.telegram.bots.v2.methods._
import info.mukel.telegram.bots.v2.model.InputFile
import org.json4s._
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TelegramApiAkka(token: String) {

  private val apiBaseUrl = s"https://api.telegram.org/bot$token/"

  /** Extract request URL from class name.
    */
  private def getRequestUrl[R](r: ApiRequest[R]): String = apiBaseUrl + r.getClass.getSimpleName.reverse.dropWhile(_ == '$').reverse

  /**
    * Transparent camelCase <-> underscore_case conversions during serialization/deserialization
    */
  implicit val formats = Serialization.formats(NoTypeHints) +
    new EnumNameSerializer(ChatAction) +
    new EnumNameSerializer(ParseMode)

  private implicit def camelCaseJsonUnmarshaller[T: Manifest](implicit formats: Formats): FromEntityUnmarshaller[T] = {
    Unmarshaller
      .byteStringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .mapWithCharset((data, charset) => {
        val json = data.decodeString(charset.nioCharset.name)
        println("RECEIVED JSON" + json)
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

                /* Doesn't work
              case InputFile.FromContent(content) =>
                Multipart.FormData.BodyPart.apply(key, HttpEntity(content))
                */
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

  private implicit val system = ActorSystem()
  private implicit val materializer = ActorMaterializer()

  private val http = Http(system)

  def toHttpRequest[R: Manifest](r: ApiRequest[R]): Future[HttpRequest] = {
    val requestEntity = r match {
      case multipartRequest: ApiRequestMultipart[R] =>
        Marshal(multipartRequest).to[RequestEntity]

      case jsonRequest: ApiRequestJson[R] =>
        Marshal(jsonRequest).to[RequestEntity]
    }

    requestEntity map {
      re =>
        HttpRequest(HttpMethods.POST, Uri(getRequestUrl(r)), entity = re)
    }
  }

  private def toApiResponse[R: Manifest](httpResponse: HttpResponse): Future[ApiResponse[R]] = {
    Unmarshal(httpResponse.entity).to[ApiResponse[R]]
  }

  /**
    * Spawns a type-safe request.
    *
    * @param request
    * @tparam R The request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  def request[R: Manifest](request: ApiRequest[R]): Future[R] = {
    toHttpRequest(request)
      .flatMap(http.singleRequest(_))
      .flatMap(toApiResponse[R])
      .flatMap {
        case ApiResponse(true, Some(result), _, _) =>
          Future.successful(result)

        case ApiResponse(false, _, description, errorCode) =>
          Future.failed(
            new TelegramApiException(description.getOrElse("Invalid/empty response"))
          )

        // case _ => // Probably a de/serialization error, just raise MatchError
      }
  }
}
