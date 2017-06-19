package info.mukel.telegrambot4s.examples

import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._
import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
  * Spotify search and play previews.
  */
class SpotifyBot(token: String) extends ExampleBot(token) with Polling {

  val limit = 20

  override def receiveInlineQuery(inlineQuery: InlineQuery): Unit = {
    super.receiveInlineQuery(inlineQuery)
    val query = inlineQuery.query
    val offset = Extractor.Int.unapply(inlineQuery.offset).getOrElse(0)

    val url = s"https://api.spotify.com/v1/search?type=track&limit=$limit&offset=$offset&q=${URLEncoder.encode(query)}"

    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      jsonText <- Unmarshal(response).to[String]
    } yield {

      val results = for {
        JArray(tracks) <- (parse(jsonText) \ "tracks" \ "items")
        trackObj <- tracks
        JObject(track) <- trackObj
        JField("id", JString(id)) <- track
        JField("name", JString(title)) <- track
        JField("preview_url", JString(preview_url)) <- track
        JString(artist) <- ((trackObj \ "artists") (0) \ "name")
      } yield
        InlineQueryResultAudio(id, preview_url, title, artist, audioDuration = 30)

      request(
        AnswerInlineQuery(
          inlineQuery.id,
          results,
          nextOffset = (offset + limit).toString
        )
      )
    }
  }
}
