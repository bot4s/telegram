package info.mukel.telegrambot4s.api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.clients.AkkaClient

import scala.concurrent.ExecutionContext

/** Telegram Bot with "sane" defaults.
  */
trait TelegramBot extends BotBase with AkkaDefaults with GlobalExecutionContext {
  override val logger = Logger(getClass)
  override val client: RequestHandler = new AkkaClient(token)
}

trait BotExecutionContext {
  implicit val executionContext: ExecutionContext
}

trait GlobalExecutionContext extends BotExecutionContext {
  implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global
}

trait AkkaImplicits {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
}

trait AkkaDefaults extends AkkaImplicits {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
}
