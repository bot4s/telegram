package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.{ApiRequest, ApiResponse}

import scala.concurrent.Future

abstract class RequestHandler {
  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  def apply[R: Manifest](request: ApiRequest[R]): Future[R]


  def processApiResponse[R: Manifest](response: ApiResponse[R]): R = response match {
    case ApiResponse(true, Some(result), _, _, _) =>
      result
    case ApiResponse(false, _, description, Some(errorCode), parameters) =>
      val e = TelegramApiException(description.getOrElse("Unexpected/invalid/empty response"), errorCode, None, parameters)
      throw e
    case _ =>
      val msg = "Error on request response"
      throw new RuntimeException(msg)
  }

}
