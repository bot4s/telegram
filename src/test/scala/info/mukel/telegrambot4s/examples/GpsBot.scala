package info.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._
import info.mukel.telegrambot4s.api.{ChatActions, Commands, Polling}
import info.mukel.telegrambot4s.Implicits
import info.mukel.telegrambot4s.methods.{SendLocation, SendMessage}
import info.mukel.telegrambot4s.models.{KeyboardButton, Message, ReplyKeyboardMarkup}

object GpsBot extends TestBot with Polling with Commands with ChatActions {

  on("/gps") { implicit msg => _ =>
    findingLocation
    val markup = ReplyKeyboardMarkup(Seq(Seq(KeyboardButton("Share location", requestLocation = true))), oneTimeKeyboard = true)
    api.request(SendMessage(msg.sender, "Location", replyMarkup = markup))
  }

  override def handleMessage(msg: Message) = {
    for (location <- msg.location) {
      api.request(SendLocation(msg.sender, location.latitude, location.longitude))
    }
  }
}
