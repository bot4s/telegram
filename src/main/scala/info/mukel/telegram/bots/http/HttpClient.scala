package info.mukel.telegram.bots.http

import scala.concurrent.Future

/**
 * HttpClient
 *
 * Provides HTTP request methods (to be backed by Scalaj-Http, Dispatch or mock objects for testing)
 */
trait HttpClient {
  /**
   * request
   *
   * All of the complexity of the whole query system resides here.
   *
   * The parameters passed in [options] are handled as follows:
   *   (id, file: InputFile)  file content is injected as multipart form data.
   *   (id, None)             is ignored (= the parameter is absent)
   *   (id, Some(x))          (given optional parameter) passed as parameter id=x.toString
   *   (id, x)                (anything else) passed as parameter id=x.toString
   */
  def request(requestUrl: String, options : (String, Any)*): String
  def asyncRequest(requestUrl: String, options : (String, Any)*): Future[String]
}
