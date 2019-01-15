package com.bot4s.telegram.api

import akka.actor.ActorRef
import com.bot4s.telegram.models.{Update, User}

import scala.concurrent.Future

/**
  * Provides bare-bones harness for actors.
  * Redirects all Updates to a broker actor.
  */
trait ActorBroker extends BotBase[Future] {

  def broker: Option[ActorRef]

  override def receiveUpdate(u: Update, botUser: Option[User]): Future[Unit] = {
    broker.foreach(_ ! u)
    super.receiveUpdate(u, botUser)
  }
}
