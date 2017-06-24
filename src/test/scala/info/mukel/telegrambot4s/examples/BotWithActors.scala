package info.mukel.telegrambot4s.examples
/*
import akka.actor.{Actor, ActorRef, FSM, Props}
import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.actors.ActorBroker
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative._
import info.mukel.telegrambot4s.methods.{EditMessageReplyMarkup, SendMessage}
import info.mukel.telegrambot4s.models._

import scala.collection.mutable
import scala.util.Success

sealed trait GameState
case object Playing extends GameState
case object Idle extends GameState

case class StartGame(msg: Message)

object TicTacToe {
  val X = 'X'
  val O = 'O'
  val EmptyBoard = ('1' to '9').toList
  val winnerLines = List((0,1,2), (3,4,5), (6,7,8), (0,3,6), (1,4,7), (2,5,8), (0,4,8), (2,4,6))
  val rnd = new util.Random(System.currentTimeMillis)
}

case class Board(board : List[Char] = TicTacToe.EmptyBoard) {
  import TicTacToe._
  def availableMoves = board.filter(c => c != X && c != O)
  def availableMovesIdxs = for ((c,i) <- board.zipWithIndex if c != X && c != O) yield i
  def computerPlays = new Board(board.updated(availableMovesIdxs(rnd.nextInt(availableMovesIdxs.length)), O))
  def humanPlays(move : Char) = new Board(board.updated(board.indexOf(move), X))
  def isDraw = board.forall(c => c == X || c == O)
  def isWinner(winner : Char) = winnerLines.exists { case (i,j,k) => board(i) == winner && board(j) == winner && board(k) == winner}
  def isOver = isWinner(O) || isWinner(X) || isDraw
  def markup = InlineKeyboardMarkup(board.map(x => InlineKeyboardButton.callbackData(x+"", x+"")).grouped(3).toSeq)
  def printOverMessage = {
    if (isWinner(X)) "You win."
    else if (isWinner(O)) "Computer wins."
    else if (isDraw) "It's a draw."
    else "Not over yet, or something went wrong."
  }
}

class ActorBot(token: String) extends ExampleBot(token) with Polling with ActorBroker with Callbacks with ChatActions {

  override val broker = Some(system.actorOf(Props(new Broker), "broker"))

  class Broker extends Actor {
    val chats = mutable.Map[Long, ActorRef]()
    override def receive = {
      case u : Update =>
        for (m <- u.message) {
          val actor = chats.getOrElseUpdate(m.source,
            system.actorOf(Props(new TicTacToeActor), s"${m.source}-chatter"))
          actor ! m
        }
        for (cbq <- u.callbackQuery) {
          val actor = chats.getOrElseUpdate(cbq.message.map(_.source).getOrElse(0L),
            system.actorOf(Props(new TicTacToeActor), "chatter"))

          println(actor)
          actor ! cbq
        }
    }
  }

  class TicTacToeActor extends Actor with FSM[GameState, Board] {

    var gameMsg: Message = _

    startWith(Idle, Board())

    when(Playing) {
      case Event(cbq : CallbackQuery, board) =>
        cbq.data.map { data =>

          val nextBoard = {
            val b = board.humanPlays(data(0))
            if (b.isOver) b else b.computerPlays
          }

          ackCallback("Hello")(cbq)

          if (nextBoard.isOver) {
            request(SendMessage(gameMsg.source, nextBoard.printOverMessage))
            goto(Idle)
          } else
            goto(Playing) using nextBoard



        } getOrElse stay()

      case other => println("Playing: " + other)
        stay()
    }

    when(Idle) {
      case Event(m @ Command("/start", _), _) =>
        for (msg <- request(SendMessage(m.source, "Starting!")))
          self ! StartGame(msg)

        stay()

      case Event(StartGame(msg), _) =>
        gameMsg = msg
        goto(Playing) using Board()

      case other => println("Idle: " + other)
        goto(Playing) using Board()
    }

    onTransition {
      case (Playing -> _) | (Idle -> Playing) =>
        request(EditMessageReplyMarkup(chatId = gameMsg.chat.id, messageId = gameMsg.messageId, replyMarkup = nextStateData.markup))
    }

    initialize()
  }
}
*/