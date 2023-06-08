import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models._

import scala.concurrent.Future
import java.nio.file.Files
import java.nio.file.Path

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
        request(SendSticker(msg.chat.id, InputFile("https://www.gstatic.com/webp/gallery/1.webp"))).void
      case Some("/create_sticker_set") =>
        createStickerSet(msg)
      case Some("/delete_sticker_set") =>
        request(GetMe).flatMap { me =>
          request(DeleteStickerSet(f"api_by_${me.username.mkString}"))
        }.void

      case _ => Future.successful(())
    }

  private def createStickerSet(msg: Message) = {
    val stickerResource =
      InputFile(
        "sticker.webp",
        Files.readAllBytes(Path.of(getClass.getResource("stickers/sticker.webp").toURI))
      )
    val sticker2Resource = InputFile(Path.of(getClass.getResource("stickers/sticker.png").toURI))

    for {
      me       <- request(GetMe)
      user      = msg.from.get
      sticker  <- request(UploadStickerFile(user.id, stickerResource, StickerFormat.Static))
      sticker2 <- request(UploadStickerFile(user.id, sticker2Resource, StickerFormat.Static))
      stickerSet <- request(
                      CreateNewStickerSet(
                        user.id,
                        f"api_by_${me.username.mkString}",
                        "Telegram BoT Sticker set",
                        Array[InputSticker](
                          InputSticker(InputFile(sticker.fileId), Array("😂")),
                          InputSticker(InputFile(sticker2.fileId), Array("😀"))
                        ),
                        stickerFormat = StickerFormat.Static
                      )
                    )
      // Making sure that we can send a file (sticker in this case), using the three possible ways
      _ <- request(SendSticker(msg.chat.id, InputFile(sticker.fileId)))
      _ <- request(SendSticker(msg.chat.id, stickerResource))
      _ <- request(SendSticker(msg.chat.id, sticker2Resource))
    } yield ()
  }
}
