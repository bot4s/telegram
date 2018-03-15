package info.mukel.telegrambot4s.akka

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.RequestEntity
import akka.http.scaladsl.testkit.ScalatestRouteTest

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.RequestEntity
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest

class AkkMarshalling extends ScalatestRouteTest with TestUtils {
  import info.mukel.telegrambot4s.akka.marshalling.AkkaHttpMarshalling.underscore_case_marshaller

  Post("/", Marshal(entity).to[RequestEntity]) ~> {
    formFields(('caption, 'chat_id, 'document)) {
      (caption, chat_id, document) => complete(caption + chat_id + document)
    }
  } ~> check { responseAs[String] shouldEqual captionWithLineBreak + channelId + fileId }
}
