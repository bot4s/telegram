package info.mukel.telegrambot4s.clients

import java.net.Proxy
import java.util.UUID

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.api.RequestHandler
import info.mukel.telegrambot4s.marshalling.ScalajHttpMarshalling
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}
import scalaj.http.HttpRequest

import scala.concurrent.{ExecutionContext, Future, blocking}
import scala.util.{Failure, Success}

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
class ScalajHttpClient(token: String, proxy: Proxy = Proxy.NO_PROXY, telegramHost: String = "api.telegram.org")(implicit ec: ExecutionContext) extends RequestHandler {

  import info.mukel.telegrambot4s.marshalling.JsonMarshallers._

  val connectionTimeoutMs = 10000
  val readTimeoutMs = 50000

  private val apiBaseUrl = s"https://$telegramHost/bot$token/"
  private val logger = Logger[ScalajHttpClient]

  protected def spawnRequest[R: Manifest](r: HttpRequest): Future[R] = {
    Future {
      blocking {
        r.timeout(connectionTimeoutMs, readTimeoutMs).proxy(proxy).asString
      }
    } map {
      x =>
        if (x.isSuccess)
          fromJson[ApiResponse[R]](x.body)
        else
          throw new Exception(s"Network error ${x.code} on request")
    } map (processApiResponse[R])
  }

  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  override def apply[R: Manifest](request: ApiRequest[R]): Future[R] = {
    val url = apiBaseUrl + request.methodName
    val uuid = UUID.randomUUID()
    logger.debug(s"REQUEST $uuid $request")
    val scalajRequest = ScalajHttpMarshalling.marshall(request, url)
    val f = spawnRequest(scalajRequest)
    f.onComplete {
      case Success(response) => logger.debug(s"RESPONSE $uuid $response")
      case Failure(e) => logger.debug(s"RESPONSE $uuid $e")
    }
    f
  }
}