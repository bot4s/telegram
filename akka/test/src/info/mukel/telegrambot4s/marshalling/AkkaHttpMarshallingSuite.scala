package info.mukel.telegrambot4s.marshalling

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.RequestEntity
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import info.mukel.telegrambot4s.api.TestUtils
import info.mukel.telegrambot4s.methods.SendDocument
import info.mukel.telegrambot4s.models.{AkkaInputFile, InputFile}
import org.scalatest.{FunSuite, Matchers}
import AkkaHttpMarshalling.underscore_case_marshaller

class AkkaHttpMarshallingSuite extends FunSuite with ScalatestRouteTest with Matchers with TestUtils {

  test("Correctly serialize top-level string members in Akka multipart requests") {
    val captionWithLineBreak = "this is a line\nand then\t another line"
    val channelId = "this_is_a_channel"
    val fileId = "and_a_file_id"

    val entity = SendDocument(channelId, InputFile(fileId), caption = Some(captionWithLineBreak))
    Post("/", Marshal(entity).to[RequestEntity]) ~> {
      formFields(('caption, 'chat_id, 'document)) {
        (caption, chat_id, document) => complete(caption + chat_id + document)
      }
    } ~> check {
      responseAs[String] shouldEqual (captionWithLineBreak + channelId + fileId)
    }
  }

  test("Handles AkkaInputFile") {
    val channelId = "this_is_a_channel"
    val content = "file content"
    val entity = SendDocument(channelId, AkkaInputFile("Pepe", ByteString(content)))
    Post("/", Marshal(entity).to[RequestEntity]) ~> {
      formFields('document) {
        document => complete(document)
      }
    } ~> check {
      responseAs[ByteString] shouldEqual ByteString(content)
    }
  }
}
