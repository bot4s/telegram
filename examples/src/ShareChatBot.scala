import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future

/**
 * https://core.telegram.org/bots/features#chat-and-user-selection
 */
class ShareChatBot(token: String) extends ExampleBot(token) with Polling {

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.chatShared.map { shared =>
      request(SendMessage(msg.source, s"You shared ${shared.chatId} with me")).void
    }.orElse(msg.text.map { text =>
      val btn = KeyboardButton("Select a chat", requestChat = Some(KeyboardButtonRequestChat(1, true)))
      request(
        SendMessage(
          msg.source,
          "Who do you want me to notify ?",
          replyMarkup = Some(ReplyKeyboardMarkup.singleButton(btn))
        )
      ).void
    }).getOrElse(Future.successful(()))
}
