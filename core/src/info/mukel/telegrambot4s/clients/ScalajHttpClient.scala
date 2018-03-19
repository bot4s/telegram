package info.mukel.telegrambot4s.clients

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.api.{RequestHandler, TelegramApiException}
import info.mukel.telegrambot4s.marshalling.ScalajHttpMarshalling
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}
import scalaj.http.HttpRequest
import scala.concurrent.blocking

import scala.concurrent.{ExecutionContext, Future}

/** Scalaj-http Telegram Bot API client
  *
  * Provide transparent camelCase <-> underscore_case conversions during serialization/deserialization.
  *
  * The Scalaj-http supports the following InputFiles:
  *   InputFile.FileId
  *   InputFile.Contents
  *   InputFile.Path
  *
  * @param token Bot token
  */
class ScalajHttpClient(token: String, telegramHost: String = "api.telegram.org")(implicit ec: ExecutionContext) extends RequestHandler {

  import info.mukel.telegrambot4s.marshalling.JsonMarshallers._

  private val logger = Logger.apply("ScalajHttpClient")

  val connectionTimeoutMs = 10000
  val readTimeoutMs = 50000

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"

  private def parseApiResponse[R: Manifest](apiResponse: ApiResponse[R]): R = apiResponse match {
    case ApiResponse(true, Some(result), _, _, _) => result
    case ApiResponse(false, _, description, Some(errorCode), parameters) =>
      val e = TelegramApiException(description.getOrElse("Unexpected/invalid/empty response"), errorCode, None, parameters)
      logger.error("Telegram API exception", e)
      throw e
    case _ =>
      val msg = "Unknown error on request response"
      logger.error(msg)
      throw new Exception(msg)
  }

  private def sendRequest[R: Manifest](r: HttpRequest): Future[R] = {
    Future {
      blocking {
        r.timeout(connectionTimeoutMs, readTimeoutMs).asString
      }
    } map {
      x =>
        if (x.isSuccess)
          fromJson[ApiResponse[R]](x.body)
        else
          throw new Exception(s"Network error ${x.code} on request")
    } map (parseApiResponse[R])
  }

  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  override def apply[R: Manifest](request: ApiRequest[R]): Future[R] = {
    val url = apiBaseUrl + request.methodName
    val scalajRequest = ScalajHttpMarshalling.marshall(request, url)
    sendRequest(scalajRequest)
  }
}