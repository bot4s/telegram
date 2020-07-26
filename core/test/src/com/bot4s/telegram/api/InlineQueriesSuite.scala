package com.bot4s.telegram.api

import cats.instances.future._
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.models.{InlineQuery, Update}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class InlineQueriesSuite extends AnyFlatSpec with MockFactory with TestUtils {

  trait Fixture {
    val handler = mockFunction[InlineQuery, Future[Unit]]
    val bot = new TestBot with InlineQueries[Future] with RegexCommands[Future]
  }

  "Inline query filter" should "accept matches" in new Fixture {
    val q = inlineQuery("hello")
    handler.expects(q).returning(Future.successful(())).once()
    when[Future, InlineQuery](bot.onInlineQuery, _.query == "hello")(handler)
    bot.receiveInlineQuery(q).get
  }

  it should "ignore non-matches" in new Fixture {
    handler.expects(*).never()
    when[Future, InlineQuery](bot.onInlineQuery, _.query == "hello")(handler)
    bot.receiveInlineQuery(inlineQuery("abc"))
  }

  "onInlineQuery" should "catch all messages" in new Fixture {
    val queries = (0 until 100).map (t => inlineQuery(t.toString))
    for (q <- queries)
      handler.expects(q).returning(Future.successful(())).once()
    bot.onInlineQuery(handler)
    val r = Future.traverse(queries) { q => bot.receiveUpdate(Update(123, inlineQuery = Some(q)), None) }
    r.get
  }

  "onRegexInline" should "pass matched groups" in new Fixture {
    val argsHandler = mockFunction[InlineQuery, Seq[String], Future[Unit]]
    argsHandler.expects(*, Seq("1234")).returning(Future.successful(())).once()
    bot.onRegexInline("""/cmd ([0-9]+)""".r)(argsHandler.curried)
    bot.receiveInlineQuery(inlineQuery("/cmd 1234")).get
  }
}

