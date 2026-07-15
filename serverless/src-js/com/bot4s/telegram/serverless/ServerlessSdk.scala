package com.bot4s.telegram.serverless

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("sdk", "api")
private[serverless] object ServerlessApi extends js.Object

private[serverless] object ServerlessSdk {
  val api: js.Dynamic = ServerlessApi.asInstanceOf[js.Dynamic]
}
