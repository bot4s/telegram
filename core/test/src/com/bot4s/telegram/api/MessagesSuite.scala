package com.bot4s.telegram.api

import cats.instances.future._
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.models.{Message, Update}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class MessagesSuite extends AnyFlatSpec with MockFactory with TestUtils {

  trait Fixture {
    val handler = mockFunction[Message, Future[Unit]]
    val bot = new TestBot with Messages[Future]
  }

  "A message filter " should "accept matches" in new Fixture {
    val helloMsg = textMessage("hello")
    handler.expects(helloMsg).returning(Future.successful(())).once()
    when[Future, Message](bot.onMessage, _.text.contains("hello"))(handler)
    bot.receiveMessage(helloMsg).get
  }

  it should "ignore non-matches" in new Fixture {
    handler.expects(*).never()
    when[Future, Message](bot.onMessage, _.text.contains("hello"))(handler)
    bot.receiveMessage(textMessage("abc"))
  }

  "onMessage" should "catch all messages" in new Fixture {
    val msgs = (0 until 100).map (t => textMessage(t.toString))
    for (m <- msgs)
      handler.expects(m).returning(Future.successful(())).once()
    bot.onMessage(handler)
    val r = Future.traverse(msgs) { m => bot.receiveUpdate(Update(123, Some(m)), None) }
    r.get
  }

  "editedMessage filter " should "accept matches" in new Fixture {
    val helloMsg = textMessage("hello")
    handler.expects(helloMsg).returning(Future.successful(())).once()
    when[Future, Message](bot.onEditedMessage, _.text.contains("hello"))(handler)
    bot.receiveEditedMessage(helloMsg).get
  }

  it should "ignore non-matches" in new Fixture {
    handler.expects(*).never()
    when[Future, Message](bot.onEditedMessage, _.text.contains("hello"))(handler)
    bot.receiveEditedMessage(textMessage("abc"))
  }

  "onEditedMessage" should "catch all messages" in new Fixture {
    val msgs = (0 until 100).map (t => textMessage(t.toString))
    for (m <- msgs)
      handler.expects(m).returning(Future.successful(())).once()
    bot.onEditedMessage(handler)
    val r = Future.traverse(msgs) { m => bot.receiveUpdate(Update(123, editedMessage = Some(m)), None) }
    r.get
  }
}
