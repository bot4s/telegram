package com.bot4s.telegram.api

import akka.actor.ActorRef
import com.bot4s.telegram.models.Update

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
