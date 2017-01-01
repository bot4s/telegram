package info.mukel.telegrambot4s.actors

import akka.actor.{Actor, ActorRef, Props, Terminated}
import info.mukel.telegrambot4s.api.{AkkaDefaults, BotBase, Commands}
import info.mukel.telegrambot4s.models.{Message, Update}

/**
  * Provides bare-bones harness for per-chat FSM handlers.
  */
trait ActorBroker extends BotBase {

  def broker: Option[ActorRef]

  override def onUpdate(u: Update): Unit = {
    broker.foreach(_ ! u)
    super.onUpdate(u)
  }
}
