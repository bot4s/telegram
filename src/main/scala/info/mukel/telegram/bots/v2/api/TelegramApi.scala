package info.mukel.telegram.bots.v2.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.{Marshal, Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer
import info.mukel.telegram.bots.v2.methods._
import org.json4s._
import org.json4s.ext.EnumNameSerializer
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Base type for API requests
  *
  * @tparam R Expected result type.
  *
  * All queries to the Telegram Bot API must be served over HTTPS and need to be presented in this form: https://api.telegram.org/bot<token>/METHOD_NAME. Like this for example
  *
  * https://api.telegram.org/bot123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11/getMe
  *
  * We support GET and POST HTTP methods. We support four ways of passing parameters in Bot API requests:
  *   URL query string
  *   application/x-www-form-urlencoded
  *   application/json (except for uploading files)
  *   multipart/form-data (use to upload files)
  *
  * All methods in the Bot API are case-insensitive.
  * All queries must be made using UTF-8.
  */
trait ApiRequest[R]

/** Telegram Bot API Response object
  *
  * The response contains a JSON object. If ‘ok’ equals true, the request was successful and the result of the query can be found in the ‘result’ field.
  * In case of an unsuccessful request, ‘ok’ equals false and the error is explained in the ‘description’.
  * An Integer ‘error_code’ field is also returned, but its contents are subject to change in the future.
  *
  * @param ok           Boolean Signals if the request was successful
  * @param result       Optional R Contains the response in a type-safely way
  * @param description  Optional String A human-readable description of the result
  * @param errorCode    Optional Integer Error code
  * @tparam R           Expected result type
  */
case class ApiResponse[R](
                           ok          : Boolean,
                           result      : Option[R] = None,
                           description : Option[String] = None,
                           errorCode   : Option[Int] = None
                         )

class TelegramApi(token: String) {

  private val apiBaseUrl = s"https://api.telegram.org/bot$token/"

  /** Extract request URL from class name.
    */
  private def getRequestUrl[R](r: ApiRequest[R]): String = apiBaseUrl + r.getClass.getSimpleName.reverse.dropWhile(_ == '$').reverse

  /**
    * Transparent camelCase <-> underscore_case conversions during serialization/deserialization
    */
  implicit val formats = Serialization.formats(NoTypeHints) + new EnumNameSerializer(ChatAction)

  private implicit def camelCaseJsonUnmarshaller[T : Manifest](implicit formats: Formats): FromEntityUnmarshaller[T] = {
    Unmarshaller
      .byteStringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .mapWithCharset((data, charset) => {
        val json = data.decodeString(charset.nioCharset.name)
        parse(json).camelizeKeys.extract[T]
      })
  }

  private implicit def underscore_case_json_marshaller[T <: AnyRef](implicit formats: Formats): ToEntityMarshaller[T] = {
    Marshaller
      .StringMarshaller
      .wrap(MediaTypes.`application/json`) {
        t =>
          compact(render(Extraction.decompose(t).underscoreKeys))
      }
  }

  private def toHttpRequest[R : Manifest](r: ApiRequest[R]): Future[HttpRequest] = {
    val requestEntity = Marshal(r).to[RequestEntity]
    println(requestEntity)
    requestEntity map {
      e => HttpRequest(uri = Uri(getRequestUrl(r)), entity = e)
    }
  }

  private def toApiResponse[R : Manifest](httpResponse: HttpResponse): Future[ApiResponse[R]] = {
    Unmarshal(httpResponse.entity).to[ApiResponse[R]]
  }

  private implicit val system = ActorSystem()
  private implicit val materializer = ActorMaterializer()

  private val http = Http(system)

  /**
    * Spawns a type-safe request.
    *
    * @param request
    * @tparam R       The request's expected result type
    * @return         The request result wrapped in a Future (async)
    */
  def request[R : Manifest](request: ApiRequest[R]): Future[R] = {
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
