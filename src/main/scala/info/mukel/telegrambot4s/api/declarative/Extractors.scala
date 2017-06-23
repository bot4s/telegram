package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.{InlineQuery, Message, User}

object Extractors {

  implicit class Wrapper[T, R](fun: Extractor[T, R]) {
    object ! { def unapply(t: T): Option[R] = fun(t) }
  }

  trait Pepe[T] {
    def using[R](extractor: Extractor[T, R])(action: Action[R])(implicit t: T): Unit = {
      extractor(t).foreach(action)
    }
    def when(filter: Filter[T])(action: Action[T])(implicit t: T): Unit = {
      if (filter(t))
        action(t)
    }
  }

  object InlineQuery extends Pepe[InlineQuery] {

  }

  object Message extends Pepe[Message] {
    def text: Extractor[Message, String] = _.text
    def from: Extractor[Message, User] = _.from

    def command: Extractor[Message, (String, Args)] = {
      msg =>
        textTokens(msg).map {
          tokens =>
            (tokens.head, tokens.tail)
        }
    }

    def textTokens: Extractor[Message, Args] = {
      msg =>
        msg.text.map(_.trim.split("\\s+"))
    }

    def arguments: Extractor[Message, Args] = {
      msg =>
        textTokens(msg).map(_.tail)
    }
  }
}