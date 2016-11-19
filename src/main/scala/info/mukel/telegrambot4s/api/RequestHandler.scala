package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.ApiRequest

import scala.concurrent.Future


abstract class RequestHandler {
  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  def request[R: Manifest](request: ApiRequest[R]): Future[R]
}
