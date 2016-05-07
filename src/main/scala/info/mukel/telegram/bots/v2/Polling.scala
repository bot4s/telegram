package info.mukel.telegram.bots.v2


import akka.NotUsed
import akka.stream.SourceShape
import akka.stream.scaladsl.{Broadcast, Concat, Flow, GraphDSL, Source, ZipWith}
import info.mukel.telegram.bots.v2.methods.GetUpdates
import info.mukel.telegram.bots.v2.api.Implicits._
import info.mukel.telegram.bots.v2.model.Update

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by mukel on 5/4/16.
  */
trait Polling {
  self: TelegramBot =>

  private def UpdatesGenerator: Source[Array[Update], NotUsed] = Source.fromGraph(GraphDSL.create() { implicit b =>
    import GraphDSL.Implicits._
    val bcast = b.add(Broadcast[Array[Update]](2))
    val concat = b.add(Concat[Array[Update]]())
    val start = Source.fromFuture(Future.successful(Array.empty[Update]))
    val zip = b.add(ZipWith((_: Unit, right: Array[Update]) => { println(right); right}))
    val source = Source.repeat(())
    val mapf = Flow[Array[Update]].mapAsync(1)( {
      prevUpdates =>
        val lastOffset = if (prevUpdates.isEmpty) 0 else prevUpdates.map(_.updateId).max
        println(lastOffset)
        api.request(GetUpdates(lastOffset + 1, timeout = 20))
    })
    source ~> zip.in0
    zip.out ~> mapf ~> bcast
    start ~> concat ~> zip.in1
    concat        <~ bcast
    SourceShape(bcast.out(1))
  })

  override val updates = UpdatesGenerator.mapConcat(x => scala.collection.immutable.Seq[Update](x: _*))
}