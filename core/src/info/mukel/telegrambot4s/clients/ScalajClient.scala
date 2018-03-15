package info.mukel.telegrambot4s.clients

import java.nio.file.Files

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.api.{RequestHandler, TelegramApiException}
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiRequestJson, ApiRequestMultipart, ApiResponse}
import info.mukel.telegrambot4s.models.InputFile
import scalaj.http.{Http, HttpRequest, MultiPart}

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
      r.timeout(connectionTimeoutMs, readTimeoutMs).asString
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

    request match {
      case r: ApiRequestJson[R] =>
        val jsonData = toJson(r)
        sendRequest(
          Http(url).postData(jsonData).header("content-type", "application/json")
        )

      case r: ApiRequestMultipart[R] =>

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

        val scalajRequest = params.foldLeft(Http(url)) {
          case (q, (key, value)) => value match {
            case InputFile.FileId(fileId) =>
              q.param(key, fileId)

            case InputFile.Path(path) =>
              q.postMulti(MultiPart(
                key,
                path.getFileName.toString(),
                "application/octet-stream",
                Files.newInputStream(path),
                Files.size(path),
                _ => ()))

            case InputFile.Contents(filename, contents) =>
              q.postMulti(MultiPart(key, filename, "application/octet-stream", contents))

            // Throw when using unsupported files.
            case _: InputFile =>
              throw new UnsupportedOperationException("Scalaj-http client does not support this InputFile")

            // [Bug #49] JSON-serializing top level strings causes line ends to be sent as \n.
            // Top level parameters (non-JSON entities) must be passed as is (raw).
            // Note: This fixes String parameters, string-like fields e.g. chat_id and file ids should
            // not contain line breaks or awkward characters.
            case s: String =>
              q.param(key, s)

            case other =>
              def unquote(s: String): String = {
                val quote = "\""
                s.stripSuffix(quote).stripPrefix(quote)
              }

              q.param(key, unquote(toJson(other)))
          }
        }

        sendRequest(scalajRequest)
    }
  }
}