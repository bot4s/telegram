package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.models.Message
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class CommandsSuite extends FlatSpec with MockFactory with TestUtils {

  import Extractors._

  trait Fixture {
    val handler = mockFunction[Message, Unit]
    val handlerHello = mockFunction[Message, Unit]
    val handlerHelloWorld = mockFunction[Message, Unit]
    val bot = new TestBot with Commands {
      onCommand("/hello")(handlerHello)
      onCommand("/helloWorld")(handlerHelloWorld)
    }
  }

  behavior of "Commands"

  it should "ignore non-declared commands" in new Fixture {
    handlerHello.expects(*).never()
    handlerHelloWorld.expects(*).never()
    bot.receiveMessage(msg("/cocou"))
  }

  it should "match string command" in new Fixture {
    handler.expects(*).once()
    bot.onCommand("/cmd")(handler)
    bot.receiveMessage(msg("/cmd"))
  }

  it should "match String command sequence" in new Fixture {
    handler.expects(*).twice()
    bot.onCommand("/a" :: "/b" :: Nil)(handler)
    bot.receiveMessage(msg("/a"))
    bot.receiveMessage(msg("/b"))
    bot.receiveMessage(msg("/c"))
  }

  it should "match Symbol command" in new Fixture {
    handler.expects(*).once()
    bot.onCommand('cmd)(handler)
    bot.receiveMessage(msg("/cmd"))
  }

  it should "match Symbol command sequence" in new Fixture {
    handler.expects(*).twice()
    bot.onCommand('a :: 'b :: Nil)(handler)
    bot.receiveMessage(msg("/a"))
    bot.receiveMessage(msg("/b"))
    bot.receiveMessage(msg("/c"))
  }

  it should "support @sender suffix" in new Fixture {
    val args = Seq("arg1", "arg2")
    val m = msg("  /hello@TargetBot  " + args.mkString(" "))
    handlerHello.expects(m).once()
    handlerHelloWorld.expects(*).never()
    bot.receiveMessage(m)
  }

  it should "support commands without '/' suffix" in new Fixture {
    val commandHandler = mockFunction[Message, Unit]
    commandHandler.expects(*).twice()
    bot.onCommand("command" :: "/another" :: Nil)(commandHandler)
    bot.receiveMessage(msg("command"))
    bot.receiveMessage(msg("another"))
    bot.receiveMessage(msg("/command"))
    bot.receiveMessage(msg("/another"))
    bot.receiveMessage(msg("/pepe"))
  }

  "using helper" should "execute actions on match" in new Fixture {
    val textHandler = mockFunction[String, Unit]
    textHandler.expects("123").once()
    bot.using(_.text)(textHandler)(msg("123"))
  }

  it should "ignore unmatched using statements" in new Fixture {
    bot.using(_.from)(user => fail())(msg("123"))
  }

  "textTokens" should "execute actions on match" in new Fixture {
    val argsHandler = mockFunction[Seq[String], Unit]
    argsHandler.expects(Seq("hello", "123", "abc")).once()
    bot.using(textTokens)(argsHandler)(msg("  hello 123  abc "))
  }

  "withArgs" should "pass arguments" in new Fixture {
    val argsHandler = mockFunction[Seq[String], Unit]
    argsHandler.expects(Seq("arg1", "arg2")).once()
    bot.withArgs(argsHandler)(msg("  /cmd   arg1  arg2  "))
  }
}
