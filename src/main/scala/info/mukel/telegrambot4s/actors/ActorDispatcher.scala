package info.mukel.telegrambot4s.actors

import akka.actor.ActorRef
import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.Update

/**
  * Provides bare-bones harness for per-chat FSM handlers.
  */
trait ActorDispatcher extends BotBase {

  def dispatcher: Option[ActorRef]

  override def onUpdate(u: Update): Unit = {
    dispatcher.foreach(_ ! u)
    super.onUpdate(u)
  }
}

