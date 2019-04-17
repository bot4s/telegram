package com.bot4s.telegram.api

import com.bot4s.telegram.models._

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.Future

trait TestUtils {
  def textMessage(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = Some(text))

  def user(name: String): User = User(0, false, name)

  def inlineQuery(query: String): InlineQuery = {
    InlineQuery("0", from = user("Pepe"), query = query, offset = "")
  }

  implicit class FutureOps[A](f: Future[A]) {
    def get: A = Await.result(f, 10.seconds)
  }
}
