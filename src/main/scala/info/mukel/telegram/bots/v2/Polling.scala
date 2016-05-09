package info.mukel.telegram.bots.v2

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import info.mukel.telegram.bots.v2.methods.GetUpdates
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.api.TelegramApiAkka
import info.mukel.telegram.bots.v2.model.Update

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

/**
  * Created by mukel on 5/4/16.
  */
trait Polling extends TelegramBot {

  private[this] type OffsetUpdates = Future[(Long, Array[Update])]

  private[this] val seed = Future.successful((0L, Array.empty[Update]))

  private[this] val iterator = Iterator.iterate[OffsetUpdates](seed) {
    _ flatMap {
      case (prevOffset, prevUpdates) =>
        val curOffset = prevUpdates
          .map(_.updateId)
          .fold(prevOffset)(_ max _)

        api.request(GetUpdates(curOffset + 1, timeout = 20))
          .recover {
            case e: Exception =>
              // TODO: Log error
              Array.empty[Update]
          }
          .map { (curOffset, _) }
    }
  }

  private[this] val updateGroups =
    Source.fromIterator(() => iterator)
      .mapAsync(1)(identity)
      .map(_._2)

  private[this] val updates = updateGroups.mapConcat(x => scala.collection.immutable.Seq[Update](x: _*))

  override def run(): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    updates.runForeach(handleUpdate)
  }
}
