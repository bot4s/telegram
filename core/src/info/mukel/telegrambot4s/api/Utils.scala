package info.mukel.telegrambot4s.api

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Awaitable}
import scala.util.Try

object TryAwait {
  def result[T](awaitable: Awaitable[T], atMost: Duration): Try[T] = {
    Try {
      Await.result(awaitable, atMost)
    }
  }
}
