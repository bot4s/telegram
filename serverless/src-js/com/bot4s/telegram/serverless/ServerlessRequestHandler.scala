package com.bot4s.telegram.serverless

import cats.instances.future._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.marshalling
import com.bot4s.telegram.methods.{ JsonRequest, MultipartRequest, Request, Response }
import com.bot4s.telegram.models.InputFile
import io.circe.{ Decoder, Encoder }

import scala.concurrent.{ ExecutionContext, Future }
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

class ServerlessRequestHandler(api: js.Dynamic = ServerlessSdk.api)(implicit
  ec: ExecutionContext
) extends RequestHandler[Future] {

  override def sendRequest[T <: Request: Encoder](
    request: T
  )(implicit d: Decoder[request.Response]): Future[request.Response] =
    request match {
      case _: JsonRequest =>
        call(request)

      case r: MultipartRequest =>
        val unsupportedFiles = r.getFiles.collect {
          case (name, file) if !file.isInstanceOf[InputFile.FileId] => name
        }

        if (unsupportedFiles.isEmpty) {
          call(request)
        } else {
          Future.failed(
            new UnsupportedOperationException(
              s"Telegram Serverless does not support file upload fields: ${unsupportedFiles.mkString(", ")}"
            )
          )
        }

      case other =>
        Future.failed(new IllegalArgumentException(s"Unsupported request type ${other.methodName}"))
    }

  private def call[T <: Request: Encoder](
    request: T
  )(implicit d: Decoder[request.Response]): Future[request.Response] = {
    val method = apiMethodName(request)
    val params = js.JSON.parse(marshalling.toJson(request)).asInstanceOf[js.Object]

    api
      .applyDynamic(method)(params)
      .asInstanceOf[js.Promise[js.Any]]
      .toFuture
      .map(value => decode[request.Response](value))
  }

  private def apiMethodName(request: Request): String = {
    val name = request.methodName
    name.head.toLower.toString + name.tail
  }

  private def decode[A: Decoder](value: js.Any): A =
    marshalling.fromJson[A](js.JSON.stringify(value))
}
