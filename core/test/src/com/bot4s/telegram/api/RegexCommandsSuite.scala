package com.bot4s.telegram.api

import com.bot4s.telegram.api.declarative.RegexCommands
import com.bot4s.telegram.models.Message
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class RegexCommandsSuite extends AnyFlatSpec with MockFactory with TestUtils {

  trait Fixture {
    val handler = mockFunction[Message, Seq[String], Future[Unit]]
    val bot = new TestBot with RegexCommands[Future]
  }

  behavior of "RegexCommands"

  it should "match simple regex" in new Fixture {
    handler.expects(*, Seq("/pepe")).returning(Future.successful(())).once()
    handler.expects(*, Seq("/cojo")).returning(Future.successful(())).once()

    bot.onRegex("""(/pepe|/cojo)""".r)(handler.curried)

    (for {
      // Invalid
      _ <- bot.receiveMessage(textMessage("/cocou"))
      _ <- bot.receiveMessage(textMessage("/pepecojo"))
      _ <- bot.receiveMessage(textMessage("/cojopepe"))

      // Valid
      _ <- bot.receiveMessage(textMessage("  /pepe  "))
      _ <- bot.receiveMessage(textMessage("/cojo"))
    } yield ()).get
  }

  it should "pass regex groups" in new Fixture {
    handler.expects(*, List("1234")).returning(Future.successful(())).once
    bot.onRegex("""/cmd ([0-9]+)""".r)(handler.curried)
    bot.receiveMessage(textMessage("/cmd 1234")).get
  }
}
