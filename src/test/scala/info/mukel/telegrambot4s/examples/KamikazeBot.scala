package info.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._
import info.mukel.telegrambot4s.Implicits
import info.mukel.telegrambot4s.api.Polling
import info.mukel.telegrambot4s.methods.SendMessage

/**
  * Created by mukel on 5/14/16.
  */
object KamikazeBot extends TestBot with Polling {

  override def run(): Unit = {
    super.run()
    api.request(SendMessage(0, ""))
  }

}
