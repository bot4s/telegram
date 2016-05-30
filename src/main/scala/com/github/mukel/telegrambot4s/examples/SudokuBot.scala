package com.github.mukel.telegrambot4s.examples

import com.github.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

/**
  * Created by mukel on 5/11/16.
  */
object SudokuBot extends TestBot with Polling with Commands {
  val side = 4
  val values = " " + (0 to side).mkString

  var chatId = 0L
  var messageId = 0L

  val board = "   3 34  1  2   "
    .replaceAll(" ", "0")
    .toArray
    .map(_.toInt)
    .grouped(side)
    .toArray

  def genMarkup = InlineKeyboardMarkup(
    for (r <- 0 until side) yield {
      for (c <- 0 until side) yield {
        val value = values(board(r)(c) % values.size).toString
        InlineKeyboardButton(value, None, Some(if (board(r)(c) == 0) " " else s"$r $c"))
      }
    }
  )

  on("/sudoku") { implicit message => _ =>
    api.request(SendMessage(message.sender, "Sudoku", replyMarkup = genMarkup))
      .foreach {
        msg =>
          chatId = msg.chat.id
          messageId = msg.messageId
    }
  }

  override def handleCallbackQuery(callbackQuery: CallbackQuery): Unit = {
    callbackQuery.data foreach {
      _.split(" ") match {
        case Array(row, col) =>
          val (r, c) = (row.toInt, col.toInt)
          if (board(r)(c) > 0) board(r)(c) += 1
          api.request(AnswerCallbackQuery(callbackQuery.id))
          api.request(EditMessageReplyMarkup(chatId, messageId, None, genMarkup))
      }
    }
  }

}
