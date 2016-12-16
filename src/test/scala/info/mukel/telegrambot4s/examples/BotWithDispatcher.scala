package info.mukel.telegrambot4s.examples

import akka.actor.{Actor, Props}
import info.mukel.telegrambot4s.actors.ActorDispatcher
import info.mukel.telegrambot4s.api.{AkkaDefaults, Commands, Polling}
import info.mukel.telegrambot4s.models.Update

/**
  * Dummy dispatcher that consume updates directly (no dispatch).
  */
trait SingleActorDispatcher extends ActorDispatcher with AkkaDefaults {

  class Broker extends Actor {
    override def receive = {
      case u : Update =>
        u.message.foreach(_ => println("###"))
    }
  }

  override val dispatcher = Some(system.actorOf(Props(new Broker), "broker"))
}

class BotWithDispatcher(token: String) extends TestBot(token) with Polling with Commands with SingleActorDispatcher {
  // Commands work as usual, the dispatching mechanism is just an extension point for complex use cases (FSM, per-chat requests)
  on("/hello") { implicit msg => _ =>
    reply("Hello World!")
  }
}
