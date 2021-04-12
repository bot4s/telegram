package com.bot4s.telegram.clients

import org.scalatest.flatspec.AsyncFlatSpec
import sttp.client3.testing.SttpBackendStub
import com.bot4s.telegram.methods.GetMe
import scala.concurrent.ExecutionContext


class SttpClientSuite extends AsyncFlatSpec {

  behavior of "STTP client"

  it should "correctly extract the result in sendRequest" in {
      val backend = SttpBackendStub
        .asynchronousFuture
        .whenRequestMatches(_.uri.path.contains("GetMe"))
        .thenRespondServerError()
      val client = new FutureSttpClient("")(backend, implicitly[ExecutionContext])

      recoverToSucceededIf[Exception] {
        client.apply(GetMe)
      }
  }
}