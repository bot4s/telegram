package com.bot4s.telegram.marshalling

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.RequestEntity
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.{ RouteTestTimeout, ScalatestRouteTest }
import akka.util.ByteString

import com.bot4s.telegram.api.TestUtils
import com.bot4s.telegram.marshalling.AkkaHttpMarshalling.underscore_case_marshaller
import com.bot4s.telegram.methods.SendDocument
import com.bot4s.telegram.models.{ AkkaInputFile, InputFile }
import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite

import concurrent.duration._

class AkkaHttpMarshallingSuite extends AnyFunSuite with ScalatestRouteTest with Matchers with TestUtils {

  implicit def defaultTimeout: RouteTestTimeout = RouteTestTimeout(5.seconds)

  test("Correctly serialize top-level string members in Akka multipart requests") {
    val captionWithLineBreak = "this is a line\nand then\t another line"
    val channelId            = "this_is_a_channel"
    val fileId               = "and_a_file_id"

    val entity = SendDocument(channelId, InputFile(fileId), caption = Some(captionWithLineBreak))
    Post("/", Marshal(entity).to[RequestEntity]) ~> {
      formFields("caption", "chat_id", "document") { (caption, chat_id, document) =>
        complete(caption + chat_id + document)
      }
    } ~> check {
      responseAs[String] shouldEqual (captionWithLineBreak + channelId + fileId)
    }
  }

  test("Handles AkkaInputFile") {
    val channelId = "this_is_a_channel"
    val content   = "file content"
    val entity    = SendDocument(channelId, AkkaInputFile("Pepe", ByteString(content)))
    Post("/", Marshal(entity).to[RequestEntity]) ~> {
      formFields("document") { document =>
        complete(document)
      }
    } ~> check {
      responseAs[ByteString] shouldEqual ByteString(content)
    }
  }
}
