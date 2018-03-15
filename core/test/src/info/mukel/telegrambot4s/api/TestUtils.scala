package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models._

trait TestUtils {
  def textMessage(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = Some(text))

  def user(name: String): User = User(0, false, name)

  def inlineQuery(query: String): InlineQuery = {
    InlineQuery("0", from = user("Pepe"), query = query, offset = "")
  }
}
