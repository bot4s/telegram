package info.mukel.telegrambot4s.actors

import akka.actor.ActorRef
import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.Update

/**
  * Provides bare-bones harness for per-chat FSM handlers.
  */
trait ActorBroker extends BotBase {

  def broker: Option[ActorRef]

  override def receiveUpdate(u: Update): Unit = {
    broker.foreach(_ ! u)
    super.receiveUpdate(u)
  }
}
