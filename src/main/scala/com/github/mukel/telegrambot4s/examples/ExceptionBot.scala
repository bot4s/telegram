package com.github.mukel.telegrambot4s.examples

/**
  * Created by mukel on 5/16/16.
  */

import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.TelegramApiException
import info.mukel.telegram.bots.v2.methods.SendMessage
import info.mukel.telegram.bots.v2.model._
import info.mukel.telegram.bots.v2.{Commands, Polling}

import scala.util.Failure

/**
  * Created by mukel on 5/9/16.
  */
object ExceptionBot extends TestBot with Polling with Commands {

  on("/hello") { implicit message => args =>

    reply("Hey there") onComplete {
      case Failure(tex: TelegramApiException) =>
        tex.errorCode match {
          case 439 => // Too many requests
            Thread.sleep(1000) // Stop the world
            reply("Houston, we have a congestion problem!")
          case 404 => println("Not found")
          case 401 => println("Unauthorized")
          case 400 => println("Bad Request")
          case e => println(s"Error code: $e")
        }

      case _ =>
    }
  }
}

