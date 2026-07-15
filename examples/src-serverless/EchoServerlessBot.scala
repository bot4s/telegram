import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.methods.SendMessage
import com.bot4s.telegram.models.Message
import com.bot4s.telegram.serverless.{ ServerlessBot, ServerlessContext }

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

object EchoServerlessBot extends ServerlessBot with Commands[Future] {
  onCommand("start") { implicit msg =>
    reply("Send me a message and I will echo it back.").void
  }

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text.fold(Future.successful(())) { text =>
      request(SendMessage(msg.source, text)).void
    }

  @JSExportTopLevel("message")
  def message(payload: js.Any, ctx: js.UndefOr[ServerlessContext] = js.undefined): js.Promise[Unit] =
    handleMessage(payload, ctx)
}
