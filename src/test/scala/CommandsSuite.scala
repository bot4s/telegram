import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.CommandParser
import info.mukel.telegrambot4s.models.{Chat, ChatType, Message}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class CommandsSuite extends FlatSpec with MockFactory {

  def msg(text: String): Message =
    Message(0, chat = Chat(0, ChatType.Private), date = 0, text = text)

  class Fixture {
    val handlerHello = mockFunction[Message, Seq[String], Unit]
    val handlerHelloWorld = mockFunction[Message, Seq[String], Unit]
    val bot = new TestBot {
      on("/hello")(handlerHello.curried)
      on("/helloWorld")(handlerHelloWorld.curried)
    }
  }

  behavior of "Commands"

  it should "ignore non-declared commands" in {
    val f = new Fixture
    f.handlerHello.expects(*, *).never()
    f.handlerHelloWorld.expects(*, *).never()
    f.bot.onMessage(msg("/cocou"))
  }

  it should "match the whole command" in {
    val f = new Fixture
    f.handlerHello.expects(*, *).never()
    f.handlerHelloWorld.expects(*, *).once()
    f.bot.onMessage(msg("/helloW"))
    f.bot.onMessage(msg("/hell"))
    f.bot.onMessage(msg("/helloWor"))
    f.bot.onMessage(msg("/elloWorld"))
    // Match.
    f.bot.onMessage(msg("/helloWorld"))
  }

  it should "be case insensitive" in {
    val f = new Fixture
    val helloVariants = Seq("/hello", "/HELLO", "/Hello", "/hELlO")
    f.handlerHelloWorld.expects(*, *).never()
    f.handlerHello.expects(*, *).repeat(helloVariants.size)
    helloVariants foreach {
      hello =>
        f.bot.onMessage(msg(hello))
    }
  }

  it should "support @sender suffix" in {
    val f = new Fixture
    val args = Seq("arg1", "arg2")
    val m = msg("  /hello@TargetBot  " + args.mkString(" "))
    f.handlerHello.expects(m, args).once()
    f.handlerHelloWorld.expects(*, *).never()
    f.bot.onMessage(m)
  }

  it should "ignore heading/trailing whitespace" in {
    val f = new Fixture
    val m = msg("\t  \n /hello    \t \n  ")
    f.handlerHello.expects(m, Seq()).once()
    f.handlerHelloWorld.expects(*, *).never()
    f.bot.onMessage(m)
  }

  it should "only handle the first command" in {
    val f = new Fixture
    val m = msg("/hello /helloWorld ")
    f.handlerHello.expects(m, Seq("/helloWorld")).once()
    f.handlerHelloWorld.expects(*, *).never()
    f.bot.onMessage(m)
  }

  "Hybrid parser" should "parse whole line arguments" in {
    val f = new Fixture
    val m = msg(
      """
        |  /hybrid arg1 arg2  arg3
        |this is arg4
        |
        |and arg5""".stripMargin)

    val handlerHybrid = mockFunction[Message, Seq[String], Unit]
    handlerHybrid.expects(m, Seq("arg1", "arg2", "arg3", "this is arg4", "", "and arg5")).once()

    f.handlerHello.expects(*, *).never()
    f.handlerHelloWorld.expects(*, *).never()
    f.bot.on("/hybrid", parser = CommandParser.Hybrid)(handlerHybrid.curried)
    f.bot.onMessage(m)
  }
}
