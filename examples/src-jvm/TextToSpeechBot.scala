import java.net.URLEncoder

import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.Implicits._
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.api.ChatActions
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future

/**
 * Text-to-speech bot (using Google TTS API)
 *
 * Google will rightfully block your IP in case of abuse.
 * '''Usage:''' /speak Hello World
 * '''Inline mode:''' @YourBot This is awesome
 */
class TextToSpeechBot(token: String)
    extends ExampleBot(token)
    with Polling
    with Commands[Future]
    with InlineQueries[Future]
    with ChatActions[Future] {

  def ttsUrl(text: String): String =
    s"http://translate.google.com/translate_tts?client=tw-ob&tl=en-us&q=${URLEncoder.encode(text, "UTF-8")}"

  onCommand("speak" | "say" | "talk") { implicit msg =>
    withArgs { args =>
      val text = args.mkString(" ")
      for {
        r <- Future(scalaj.http.Http(ttsUrl(text)).asBytes)
        if r.isSuccess
        bytes = r.body
        _ <- uploadingAudio // hint the user
        voiceMp3 = InputFile("voice.mp3", bytes)
        _       <- request(SendVoice(msg.source, voiceMp3))
      } yield ()
    }
  }

  def nonEmptyQuery(iq: InlineQuery): Boolean = iq.query.nonEmpty

  whenOrElse(onInlineQuery, nonEmptyQuery) { implicit iq =>
    answerInlineQuery(
      Seq(
        // Inline "playable" preview
        InlineQueryResultVoice("inline: " + iq.query, ttsUrl(iq.query), iq.query),
        // Redirection to /speak command
        InlineQueryResultArticle(
          "command: " + iq.query,
          iq.query,
          inputMessageContent = InputTextMessageContent("/speak " + iq.query),
          description = "/speak " + iq.query
        )
      )
    ).void
  } /* empty query */ {
    answerInlineQuery(Seq())(_).void
  }
}
