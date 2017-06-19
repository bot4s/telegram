package info.mukel.telegrambot4s.examples

import akka.actor.{Actor, Props}
import info.mukel.telegrambot4s.actors.ActorBroker
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{AkkaDefaults, Polling}
import info.mukel.telegrambot4s.models.Update

/**
  * Dummy dispatcher that consume updates directly (no dispatch).
  */
trait SingleActorBroker extends ActorBroker with AkkaDefaults {

  class Broker extends Actor {
    override def receive = {
      case u : Update =>
        u.message.foreach(_ => println("###"))
    }
  }

  override val broker = Some(system.actorOf(Props(new Broker), "broker"))
}

class BotWithBroker(token: String) extends ExampleBot(token) with Polling with Commands with SingleActorBroker {
  // Commands work as usual, the dispatching mechanism is just an extension point for complex use cases (FSM, per-chat requests)
  on("/hello") { implicit msg => _ =>
    reply("Hello World!")
  }
}
