package info.mukel.telegram.bots.http

import java.io.File
import java.nio.file.{Files, Paths}

import info.mukel.telegram.bots.api.InputFile

import scalaj.http.{Http, MultiPart}

/**
 * Created by mukel on 8/5/15.
 */
trait ScalajHttpClient extends HttpClient {

  def request(requestUrl: String, params : (String, Any)*): String = {
    // TODO: Set appropiate timeout values
    var query = Http(requestUrl)

    for ((id, value) <- params) {
      value match {
        case file: InputFile =>
          // TODO: Get the corret MIME type, right now the server ignored it or does some content-based MIME detection
          query = query.postMulti(MultiPart(id, file.name, file.mimeType, file.bytes))

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
