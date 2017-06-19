package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.declarative.BetterCommands
import info.mukel.telegrambot4s.models.{Chat, ChatType, Message}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class BetterCommandsSuite extends FlatSpec with MockFactory {

  def msg(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = text)

  class Fixture {
    val handlerHello = mockFunction[Message, Unit]
    val handlerHelloWorld = mockFunction[Message, Unit]
    val bot = new BotBase with BetterCommands {
      val client: RequestHandler = null
      val logger = Logger(getClass)
      def token = "token"
      on("/hello")(handlerHello)
      on("/helloWorld")(handlerHelloWorld)
    }
  }

  behavior of "BetterCommands"

  it should "ignore non-declared commands" in {
    val f = new Fixture
    f.handlerHello.expects(*).never()
    f.handlerHelloWorld.expects(*).never()
    f.bot.receiveMessage(msg("/cocou"))
  }

  it should "match string command" in {
    val f = new Fixture
    val handler = mockFunction[Message, Unit]
    handler.expects(*).once()
    f.bot.on("/cmd")(handler)
    f.bot.receiveMessage(msg("/cmd"))
  }

  it should "match String command sequence" in {
    val f = new Fixture
    val handler = mockFunction[Message, Unit]
    handler.expects(*).twice()
    f.bot.on("/a" :: "/b" :: Nil)(handler)
    f.bot.receiveMessage(msg("/a"))
    f.bot.receiveMessage(msg("/b"))
    f.bot.receiveMessage(msg("/c"))
  }

  it should "match Symbol command" in {
    val f = new Fixture
    val handler = mockFunction[Message, Unit]
    handler.expects(*).once()
    f.bot.on('cmd)(handler)
    f.bot.receiveMessage(msg("/cmd"))
  }

  it should "match Symbol command sequence" in {
    val f = new Fixture
    val handler = mockFunction[Message, Unit]
    handler.expects(*).twice()
    f.bot.on('a :: 'b :: Nil)(handler)
    f.bot.receiveMessage(msg("/a"))
    f.bot.receiveMessage(msg("/b"))
    f.bot.receiveMessage(msg("/c"))
  }

  it should "support @sender suffix" in {
    val f = new Fixture
    val args = Seq("arg1", "arg2")
    val m = msg("  /hello@TargetBot  " + args.mkString(" "))
    f.handlerHello.expects(m).once()
    f.handlerHelloWorld.expects(*).never()
    f.bot.receiveMessage(m)
  }

  it should "match using statements" in {
    val f = new Fixture
    val handler = mockFunction[String, Unit]
    handler.expects("123").once()
    f.bot.using(_.text)(handler)(msg("123"))
  }

  it should "ignore unmatched using statements" in {
    val f = new Fixture
    f.bot.using(_.from)(user => fail())(msg("123"))
  }

  it should "pass arguments using withArgs" in {
    val f = new Fixture
    val handler = mockFunction[Seq[String], Unit]
    handler.expects(Seq("arg1", "arg2")).once()
    f.bot.withArgs(handler)(msg("/cmd   arg1  arg2"))
  }
}
