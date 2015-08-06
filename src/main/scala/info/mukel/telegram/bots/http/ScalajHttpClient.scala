package info.mukel.telegram.bots.http

import java.io.File
import java.nio.file.{Files, Paths}

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

          // TODO: Get the corret MIME type, right now the server ignored it or does some content-based MIME detection
          val mimeType = "application/octet-stream"
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
      throw new Exception("HTTP request error " + response.code + ": " + response.statusLine)
  }
}
