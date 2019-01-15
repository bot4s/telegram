package com.bot4s.telegram.api

import com.bot4s.telegram.api.declarative.CommandFilterMagnet._
import com.bot4s.telegram.api.declarative.{CommandImplicits, Commands}
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.methods.{Request, GetMe}
import com.bot4s.telegram.models.{Message, User}
import com.bot4s.telegram.future.GlobalExecutionContext
import io.circe.{Decoder, Encoder}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

class CommandsSuite extends FlatSpec with MockFactory with TestUtils with CommandImplicits {

  import marshalling._

  trait Fixture {
    val handler = mockFunction[Message, Future[Unit]]
    val handlerHello = mockFunction[Message, Future[Unit]]
    val handlerHelloWorld = mockFunction[Message, Future[Unit]]
    val handlerRespect = mockFunction[Message, Future[Unit]]

    val botUser = User(123, false, "FirstName", username = Some("TestBot"))
    val bot = new TestBot with GlobalExecutionContext with Commands[Future] {
      // Bot name = "TestBot".
      override lazy val client = new RequestHandler {
        def sendRequest[R, T <: Request[_ /* R */]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): Future[R] = ???
        override def apply[R](request: Request[R]): Future[R] = request match {
          case GetMe => Future.successful({
            val jsonUser = toJson[User](botUser)
            fromJson[User](jsonUser)(userDecoder)
          })
        }
      }

      onCommand("/hello")(handlerHello)
      onCommand("/helloWorld")(handlerHelloWorld)
      onCommand("/respect" & RespectRecipient)(handlerRespect)
    }

    bot.run()
  }

  behavior of "Commands"

  it should "ignore non-declared commands" in new Fixture {
    handlerHello.expects(*).never()
    handlerHelloWorld.expects(*).never()
    bot.receiveExtMessage((textMessage("/cocou"), None)).get
  }

  it should "match string command" in new Fixture {
    handler.expects(*).returning(Future.successful(())).once()
    bot.onCommand("/cmd")(handler)
    bot.receiveExtMessage((textMessage("/cmd"), None)).get
  }

  it should "match String command sequence" in new Fixture {
    handler.expects(*).returning(Future.successful(())).twice()
    bot.onCommand("/a" | "/b")(handler)
    (for {
      _ <- bot.receiveExtMessage((textMessage("/a"), None))
      _ <- bot.receiveExtMessage((textMessage("/b"), None))
      _ <- bot.receiveExtMessage((textMessage("/c"), None))
    } yield ()).get
  }

  it should "match Symbol command" in new Fixture {
    handler.expects(*).returning(Future.successful(())).once()
    bot.onCommand('cmd)(handler)
    bot.receiveExtMessage((textMessage("/cmd"), None)).get
  }

  it should "match Symbol command sequence" in new Fixture {
    handler.expects(*).returning(Future.successful(())).twice()
    bot.onCommand('a | 'b)(handler)
    (for {
      _ <- bot.receiveExtMessage((textMessage("/a"), None))
      _ <- bot.receiveExtMessage((textMessage("/b"), None))
      _ <- bot.receiveExtMessage((textMessage("/c"), None))
    } yield ()).get
  }

  it should "support @sender suffix" in new Fixture {
    val m = textMessage("  /hello@Test_Bot  ")
    handlerHello.expects(m).returning(Future.successful(())).once()
    bot.receiveExtMessage((m, None)).get
  }

  it should "ignore case in @sender" in new Fixture {
    val args = Seq("arg1", "arg2")
    val m = textMessage("  /respect@testbot  " + args.mkString(" "))
    handlerRespect.expects(m).returning(Future.successful(())).once()
    bot.receiveExtMessage((m, None)).get
  }

  it should "accept any recipient if respectRecipient is not used" in new Fixture {
    val args = Seq("arg1", "arg2")
    val m = textMessage("  /hello@otherbot  " + args.mkString(" "))
    handlerHello.expects(m).returning(Future.successful(())).once()
    bot.receiveExtMessage((m, None)).get
  }

  it should "ignore empty @sender" in new Fixture {
    val m = textMessage("  /hello@ ")
    handlerHello.expects(m).returning(Future.successful(())).once()
    bot.receiveExtMessage((m, None)).get
  }

  it should "ignore different @sender" in new Fixture {
    val m = textMessage("  /respect@OtherBot  ")
    handlerHello.expects(m).never()
    handlerHelloWorld.expects(*).never()
    handlerRespect.expects(*).never()
    bot.receiveExtMessage((m, Some(botUser))).get
  }

  it should "support commands without '/' suffix" in new Fixture {
    val commandHandler = mockFunction[Message, Future[Unit]]
    commandHandler.expects(*).returning(Future.successful(())).twice()
    bot.onCommand("command" | "/another")(commandHandler)
    (for {
      _ <- bot.receiveExtMessage((textMessage("command"), None))
      _ <- bot.receiveExtMessage((textMessage("another"), None))
      _ <- bot.receiveExtMessage((textMessage("/command"), None))
      _ <- bot.receiveExtMessage((textMessage("/another"), None))
      _ <- bot.receiveExtMessage((textMessage("/pepe"), None))
    } yield ()).get
  }

  "using helper" should "execute actions on match" in new Fixture {
    val textHandler = mockFunction[String, Future[Unit]]
    textHandler.expects("123").once()
    bot.using(_.text)(textHandler)(textMessage("123"))
  }

  it should "ignore unmatched using statements" in new Fixture {
    bot.using(_.from)(user => fail())(textMessage("123"))
  }

  "withArgs" should "pass arguments" in new Fixture {
    val argsHandler = mockFunction[Seq[String], Future[Unit]]
    argsHandler.expects(Seq("arg1", "arg2")).once()
    bot.withArgs(argsHandler)(textMessage("  /cmd   arg1  arg2  "))
  }
}
