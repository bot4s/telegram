package info.mukel.telegram.bots.v2

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl._

/*
trait ApiRequest

trait ApiResponse

trait ApiBase {

  private val apiFlow: Flow[ApiRequest, ApiResponse, NotUsed] =
    Flow[ApiRequest]
      .via(apiRequestToHttpRequest)
      .via(http)
      .via(httpResponseToApiResponse)

  def apiRequest(request: ApiRequest): Future[ApiResponse] =
    Source.single(request)
      .via(apiFlow)
      .toMat(Sink.head)(Keep.right)
      .run()
}*/

object Test extends App {

  def getUpdates(offset: Int): Future[List[Int]] = {
    Future {
      List(offset, offset + 1)
    }
  }

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  import system.dispatcher

  val s = Source(List(1, 2, 3)).map(_ * 2).scan(0)((acc, x) => acc max x)
  s.runForeach(println)

}