import java.util.{Timer, TimerTask}

import scala.concurrent.duration.Duration
import scala.concurrent.{Future, Promise}
import scala.util.Try

object Utils {
  def after[T](duration: Duration)(block: => T): Future[T] = {
    val promise = Promise[T]()
    val t = new Timer()
    t.schedule(new TimerTask {
      override def run(): Unit = {
        promise.complete(Try(block))
      }
    }, duration.toMillis)
    promise.future
  }
}
