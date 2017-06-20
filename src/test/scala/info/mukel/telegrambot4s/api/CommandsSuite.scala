package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.declarative.BetterCommands
import info.mukel.telegrambot4s.models.{Chat, ChatType, Message}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class CommandsSuite extends FlatSpec with MockFactory {

  def msg(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = text)

  trait Fixture {
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

  behavior of "Commands"

  it should "ignore non-declared commands" in new Fixture {
    handlerHello.expects(*).never()
    handlerHelloWorld.expects(*).never()
    bot.receiveMessage(msg("/cocou"))
  }

  it should "match the whole command" in new Fixture {
    handlerHello.expects(*).never()
    handlerHelloWorld.expects(*).once()
    bot.receiveMessage(msg("/helloW"))
    bot.receiveMessage(msg("/hell"))
    bot.receiveMessage(msg("/helloWor"))
    bot.receiveMessage(msg("/elloWorld"))
    // Match.
    bot.receiveMessage(msg("/helloWorld"))
  }

  it should "be case insensitive" in new Fixture {
    val helloVariants = Seq("/hello", "/HELLO", "/Hello", "/hELlO")
    handlerHelloWorld.expects(*).never()
    handlerHello.expects(*).repeat(helloVariants.size)
    helloVariants foreach {
      hello =>
        bot.receiveMessage(msg(hello))
    }
  }

  it should "support @sender suffix" in new Fixture {
    val args = Seq("arg1", "arg2")
    val m = msg("  /hello@TargetBot  " + args.mkString(" "))
    handlerHello.expects(m).once()
    handlerHelloWorld.expects(*).never()
    bot.receiveMessage(m)
  }

  it should "ignore heading/trailing whitespace" in new Fixture {
    val m = msg("\t  \n /hello    \t \n  ")
    handlerHello.expects(m).once()
    handlerHelloWorld.expects(*).never()
    bot.receiveMessage(m)
  }

  it should "only handle the first command" in new Fixture {
    val m = msg("/hello /helloWorld ")
    handlerHello.expects(m).once()
    handlerHelloWorld.expects(*).never()
    bot.receiveMessage(m)
  }
}
