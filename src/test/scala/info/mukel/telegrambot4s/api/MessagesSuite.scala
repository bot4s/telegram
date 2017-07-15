package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.api.declarative._
import info.mukel.telegrambot4s.models.{Message, Update}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class MessagesSuite extends FlatSpec with MockFactory with TestUtils {

  trait Fixture {
    val handler = mockFunction[Message, Unit]
    val bot = new TestBot with Messages
  }

  "A message filter " should "accept matches" in new Fixture {
    val helloMsg = textMessage("hello")
    handler.expects(helloMsg).once()
    when[Message](bot.onMessage, _.text.contains("hello"))(handler)
    bot.receiveMessage(helloMsg)
  }

  it should "ignore non-matches" in new Fixture {
    handler.expects(*).never()
    when[Message](bot.onMessage, _.text.contains("hello"))(handler)
    bot.receiveMessage(textMessage("abc"))
  }

  "onMessage" should "catch all messages" in new Fixture {
    val msgs = (0 until 100).map (t => textMessage(t.toString))
    for (m <- msgs)
      handler.expects(m).once()
    bot.onMessage(handler)
    for (m <- msgs)
      bot.receiveUpdate(Update(123, Some(m)))
  }

  "editedMessage filter " should "accept matches" in new Fixture {
    val helloMsg = textMessage("hello")
    handler.expects(helloMsg).once()
    when[Message](bot.onEditedMessage, _.text.contains("hello"))(handler)
    bot.receiveEditedMessage(helloMsg)
  }

  it should "ignore non-matches" in new Fixture {
    handler.expects(*).never()
    when[Message](bot.onEditedMessage, _.text.contains("hello"))(handler)
    bot.receiveEditedMessage(textMessage("abc"))
  }

  "onEditedMessage" should "catch all messages" in new Fixture {
    val msgs = (0 until 100).map (t => textMessage(t.toString))
    for (m <- msgs)
      handler.expects(m).once()
    bot.onEditedMessage(handler)
    for (m <- msgs)
      bot.receiveUpdate(Update(123, editedMessage = Some(m)))
  }
}
