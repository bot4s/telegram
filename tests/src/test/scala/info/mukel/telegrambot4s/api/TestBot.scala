package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.models._

class TestBot extends BotBase {
  override lazy val client: RequestHandler = ???
  lazy val logger = ???
  def token = ???
}

trait TestUtils {
  def textMessage(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = Some(text))

  def user(name: String): User = User(0, name)

  def inlineQuery(query: String): InlineQuery = {
    InlineQuery("0", from = user("Pepe"), query = query, offset = "")
  }
}
