package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.clients.AkkaClient

/**
  * 'Live' layer (network + logging), backed by Akka-Http for bots.
  */
trait LiveEnvironment extends AkkaDefaults {
  _ : BotBase =>

  override val logger = Logger(getClass)
  override val client: RequestHandler = new AkkaClient(token)
}
