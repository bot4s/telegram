package info.mukel.telegrambot4s.examples

import akka.actor.{Actor, ActorRef, Props, Terminated}
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.actors.ActorBroker
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{AkkaDefaults, Polling}
import info.mukel.telegrambot4s.methods.SendMessage
import info.mukel.telegrambot4s.models.{Message, Update}

class PerChatRequestsBot(token: String) extends ExampleBot(token) with Polling with Commands with PerChatRequests {

  // Commands work as usual.
  on("/hello") { implicit msg => _ =>
    reply("Hello World!")
  }
}

trait PerChatRequests extends ActorBroker with Commands with AkkaDefaults {

  class Broker extends Actor {
    val chatActors = collection.mutable.Map[Long, ActorRef]()

    def receive = {
      case u : Update =>
        u.message.foreach { m =>
          val id = m.chat.id
          val handler = chatActors.getOrElseUpdate(m.chat.id, {
            val worker = system.actorOf(Props(new Worker), s"worker_$id")
            context.watch(worker)
            worker
          })
          handler ! m
        }

      case Terminated(worker) =>
        // This should be faster
        chatActors.find(_._2 == worker).foreach {
          case (k, _) => chatActors.remove(k)
        }

      case _ =>
    }
  }

  // Fo every chat a new worker actor will be spawned.
  // All requests will be routed through this worker actor; allowing to maintain a per-chat state.
  class Worker extends Actor {
    def receive = {
      case m : Message =>
        request(SendMessage(m.source, self.toString))

      case _ =>
    }
  }

  override val broker = Some(system.actorOf(Props(new Broker), "broker"))
}
