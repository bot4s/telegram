import java.io.File
import java.net.URLConnection
import java.nio.file.{Paths, Files}
import scalaj.http.{Http, MultiPart}

/**
 * Created by mukel on 8/5/15.
 */

trait ScalajHttpClient extends HttpClient {

  def request(requestUrl: String, options : (String, Any)*): String = {
    // TODO: Set appropiate timeout values
    var query = Http(requestUrl).timeout(10000, 20000)

    for ((id, value) <- options) {
      value match {
        case file: File =>
          // post file as multipart form data
          val byteArray = Files.readAllBytes(Paths.get(file.getAbsolutePath))
          val fileName = file.getName
          val mimeType = URLConnection.guessContentTypeFromName(fileName)
          query = query.postMulti(MultiPart(id, fileName, mimeType, byteArray))

        case Some(s) =>
          query = query.param(id, s.toString)

        case None => // ignore

        case _ =>
          query = query.param(id, value.toString)
      }
    }

    val response = query.asString
    if (response.isSuccess)
      response.body
    else
      throw new Exception("Request error: Code " + response.code)
  }
}
