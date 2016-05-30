package com.github.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

import scala.util.Failure

/**
  * Shows exception handling
  */
object ExceptionBot extends TestBot with Polling with Commands {
  on("/hello") { implicit msg => _ =>
    reply("Hey there") onComplete {
      case Failure(tex: TelegramApiException) =>
        tex.errorCode match {
          case 439 => // Too many requests
            Thread.sleep(1000) // Stop the world
            reply("Houston, we have a (congestion) problem!")
          case 404 => println("Not found")
          case 401 => println("Unauthorized")
          case 400 => println("Bad Request")
          case e => println(s"Error code: $e")
        }
      case _ =>
    }
  }
}

