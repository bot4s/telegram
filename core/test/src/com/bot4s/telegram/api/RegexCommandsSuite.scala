package com.bot4s.telegram.api

import com.bot4s.telegram.api.declarative.RegexCommands
import com.bot4s.telegram.models.Message
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class RegexCommandsSuite extends FlatSpec with MockFactory with TestUtils {

  trait Fixture {
    val handler = mockFunction[Message, Seq[String], Unit]
    val bot = new TestBot with RegexCommands
  }

  behavior of "RegexCommands"

  it should "match simple regex" in new Fixture {
    handler.expects(*, Seq("/pepe")).once()
    handler.expects(*, Seq("/cojo")).once()

    bot.onRegex("""(/pepe|/cojo)""".r)(handler.curried)

    // Invalid
    bot.receiveMessage(textMessage("/cocou"))
    bot.receiveMessage(textMessage("/pepecojo"))
    bot.receiveMessage(textMessage("/cojopepe"))

    // Valid
    bot.receiveMessage(textMessage("  /pepe  "))
    bot.receiveMessage(textMessage("/cojo"))
  }

  it should "pass regex groups" in new Fixture {
    handler.expects(*, Seq("1234")).once()
    bot.onRegex("""/cmd ([0-9]+)""".r)(handler.curried)
    bot.receiveMessage(textMessage("/cmd 1234"))
  }
}
