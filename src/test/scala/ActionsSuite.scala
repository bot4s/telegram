import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.{Actions, BotBase, Commands, RequestHandler}
import info.mukel.telegrambot4s.models.{Chat, ChatType, Message}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

import scala.concurrent.Future

trait TestEnvironment {
  _ : BotBase =>
  override val client: RequestHandler = null
  override val logger = Logger(getClass)
}

class TestBot extends BotBase with TestEnvironment with Actions with Commands {
  def token = "token"
  override def shutdown(): Future[Unit] = { Future.successful(()) }
}

class ActionSuite extends FlatSpec with MockFactory {

  val helloMsg = Message(0, chat = Chat(0, ChatType.Private), date = 0, text = "hello")
  val noHelloMsg = helloMsg.copy(text = "Bye bye!")

  class Fixture {
    val handler = mockFunction[Message, Unit]
    val bot = new TestBot { when(_.text ?== "hello")(handler) }
  }

  "An action filter " should "ignore non-matches" in {
    val f = new Fixture
    f.handler.expects(*).never()
    f.bot.onMessage(noHelloMsg)
  }

  it should "accept matches" in {
    val f = new Fixture
    f.handler.expects(helloMsg).returning(()).once()
    f.bot.onMessage(helloMsg)
  }
}
