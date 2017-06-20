package info.mukel.telegrambot4s.api
import info.mukel.telegrambot4s.models.Update

import scala.concurrent.Future
import scala.util.Failure

/**
  * Asynchronify update handlers.
  * It affects all handlers since all of them are called by receiveUpdate directly.
  * Mix it last.
  */
trait AsyncUpdates extends BotBase with AkkaImplicits {
  abstract override def receiveUpdate(u: Update): Unit = {
    Future(super.receiveUpdate(u)).onComplete {
      case Failure(e) => logger.error("Exception in update handler", e)
      case _ =>
    }
  }
}
