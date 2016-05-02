package info.mukel.telegram.bots.v2.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.{Marshal, Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer
import info.mukel.telegram.bots.v2.model._
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Base type for API requests.
  *
  * @tparam R Expected result
  */
trait ApiRequest[R]

case class ApiResponse[R](ok: Boolean, result: Option[R] = None, description: Option[String] = None)

class TelegramApi(token: String) {

  private val apiBaseUrl = s"https://api.telegram.org/bot$token/"

  /**
    * Transparent camelCase <-> underscore_case conversions during serialization/deserialization
    */
  implicit val formats = DefaultFormats

  implicit def camelCaseJsonUnmarshaller[T : Manifest](implicit formats: Formats): FromEntityUnmarshaller[T] =
    Unmarshaller
      .byteStringUnmarshaller
      .forContentTypes(ContentTypes.`application/json`)
      .mapWithCharset((data, charset) => {
        val s = data.decodeString(charset.nioCharset.name)
        parse(s).camelizeKeys.extract[T]
      })

  implicit def underscore_case_json_marshaller[T <: AnyRef](implicit formats: Formats): ToEntityMarshaller[T] =
    Marshaller.StringMarshaller.wrap(MediaTypes.`application/json`) {
      t => compact(render(Extraction.decompose(t).underscoreKeys))
    }

  private def toHttpRequest[R : Manifest](r: ApiRequest[R]): Future[HttpRequest] = {
    val url = (apiBaseUrl + r.getClass.getSimpleName).reverse.dropWhile(_ == '$').reverse
    val requestEntity = Marshal(r).to[RequestEntity]
    requestEntity map {
      e => HttpRequest(uri = Uri(url), entity = e)
    }
  }

  private def toApiResponse[R : Manifest](httpResponse: HttpResponse): Future[ApiResponse[R]] = {
    Unmarshal(httpResponse.entity).to[ApiResponse[R]]
  }

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  private val http = Http(system)

  def request[R : Manifest](request: ApiRequest[R]): Future[R] = {
    toHttpRequest(request)
      .flatMap(http.singleRequest(_))
      .flatMap(toApiResponse[R])
      .flatMap  {
        case ApiResponse(true, Some(result), _) => Future.successful(result)
        case ApiResponse(false, _, Some(description)) => Future.failed(new Exception(description))
        case _ => Future.failed(new Exception("Invalid/empty response"))
      }
  }
}