import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future

class Api66Bot(token: String) extends ExampleBot(token) with Polling {

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text match {
      case Some("/set_short_description") => request(SetMyShortDescription(Some("This is my short description"))).void
      case Some("/set_description")       => request(SetMyDescription(Some("This is my long description"))).void
      case Some("/get_short_description") =>
        request(GetMyShortDescription())
          .flatMap(description =>
            request(SendMessage(msg.chat.id, s"My short description is: ${description.shortDescription}"))
          )
          .void
      case Some("/get_description") =>
        request(GetMyDescription())
          .flatMap(description => request(SendMessage(msg.chat.id, s"My description is: ${description.description}")))
          .void
      case Some("/send_sticker") =>
        request(SendSticker(msg.chat.id, InputFile.FileId("https://www.gstatic.com/webp/gallery/1.webp"))).void
      case _ => Future.successful(())
    }
}
