package com.bot4s.telegram.clients

import org.scalatest.flatspec.AsyncFlatSpec
import sttp.client4.testing.BackendStub
import com.bot4s.telegram.methods.GetMe
import scala.concurrent.ExecutionContext
import io.circe.ParsingFailure
import com.bot4s.telegram.api.TelegramApiException

class SttpClientSuite extends AsyncFlatSpec {

  behavior of "STTP client"

  it should "fail with a ParsingFailure in case of server error" in {
    val backend = BackendStub.asynchronousFuture
      .whenRequestMatches(_.uri.path.contains("GetMe"))
      .thenRespondServerError()
    val client = new FutureSttpClient("")(backend, implicitly[ExecutionContext])

    recoverToSucceededIf[ParsingFailure] {
      client.apply(GetMe)
    }
  }

  it should "fail with a TelegramApiException the API returned an error" in {
    val backend = BackendStub.asynchronousFuture
      .whenRequestMatches(_.uri.path.contains("GetMe"))
      .thenRespondAdjust("""{"ok":false,"error_code":401,"description":"Unauthorized"}""")
    val client = new FutureSttpClient("")(backend, implicitly[ExecutionContext])

    recoverToSucceededIf[TelegramApiException] {
      client.apply(GetMe)
    }

  }
}
