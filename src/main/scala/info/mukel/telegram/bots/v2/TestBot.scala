package info.mukel.telegram.bots.v2

import java.net.URLEncoder

import info.mukel.telegram.bots.v2.methods.{AnswerCallbackQuery, SendMessage}
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.examples._

object TestBot extends App {
  WebhookBot.run()
}