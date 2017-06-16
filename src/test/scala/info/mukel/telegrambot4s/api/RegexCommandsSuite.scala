package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.models.{Chat, ChatType, Message}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class RegexCommandsSuite extends FlatSpec with MockFactory {

  def msg(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = text)

  class Fixture {
    val bot = new BotBase with RegexCommands {
      val client: RequestHandler = null
      val logger = Logger(getClass)
      def token = "token"
    }
  }

  behavior of "RegexCommands"

  it should "match simple regex" in {
    val f = new Fixture
    val handler = mockFunction[Message, Seq[String], Unit]
    handler.expects(*, *).twice()
    f.bot.onRegex("""/pepe|/cojo""".r)(handler.curried)
    f.bot.onMessage(msg("/cocou"))
    f.bot.onMessage(msg("  /pepe  "))
    f.bot.onMessage(msg("/cojo"))
  }

  it should "pass regex groups" in {
    val f = new Fixture
    val handler = mockFunction[Message, Seq[String], Unit]
    handler.expects(*, Seq("1234")).once()
    f.bot.onRegex("""/cmd ([0-9]+)""".r)(handler.curried)
    f.bot.onMessage(msg("/cmd 1234"))
  }
}
