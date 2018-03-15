package info.mukel.telegrambot4s.api

import com.typesafe.scalalogging.Logger
import info.mukel.telegrambot4s.models._

class TestBot extends BotBase {
  override lazy val client: RequestHandler = ???
  lazy val logger = Logger[TestBot]
  def token = ???
}
