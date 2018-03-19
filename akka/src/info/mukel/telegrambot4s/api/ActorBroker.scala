package info.mukel.telegrambot4s.api

import akka.actor.ActorRef
import info.mukel.telegrambot4s.models.Update

/**
  * Provides bare-bones harness for actors.
  * Redirects all Updates to a broker actor.
  */
trait ActorBroker extends BotBase {

  def broker: Option[ActorRef]

  override def receiveUpdate(u: Update): Unit = {
    broker.foreach(_ ! u)
    super.receiveUpdate(u)
  }
}
