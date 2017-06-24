package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.api.declarative.{InlineQueries, RegexCommands}
import info.mukel.telegrambot4s.models.{InlineQuery, Update}
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class InlineQueriesSuite extends FlatSpec with MockFactory with TestUtils {

  trait Fixture {
    val handler = mockFunction[InlineQuery, Unit]
    val bot = new TestBot with InlineQueries with RegexCommands
  }

  "Inline query filter" should "accept matches" in new Fixture {
    val q = inlineQuery("hello")
    handler.expects(q).once()
    bot.whenInlineQuery(_.query == "hello")(handler)
    bot.receiveInlineQuery(q)
  }

  it should "ignore non-matches" in new Fixture {
    handler.expects(*).never()
    bot.whenInlineQuery(_.query == "hello")(handler)
    bot.receiveInlineQuery(inlineQuery("abc"))
  }

  "onInlineQuery" should "catch all messages" in new Fixture {
    val queries = (0 until 100).map (t => inlineQuery(t.toString))
    for (q <- queries)
      handler.expects(q).once()
    bot.onInlineQuery(handler)
    for (q <- queries)
      bot.receiveUpdate(Update(123, inlineQuery = Some(q)))
  }

  "onRegexInline" should "pass matched groups" in new Fixture {
    val argsHandler = mockFunction[InlineQuery, Seq[String], Unit]
    argsHandler.expects(*, Seq("1234")).once()
    bot.onRegexInline("""/cmd ([0-9]+)""".r)(argsHandler.curried)
    bot.receiveInlineQuery(inlineQuery("/cmd 1234"))
  }
}

