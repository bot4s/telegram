package com.bot4s.telegram.methods

/** Use this method to remove webhook integration if you decide to switch back to getUpdates.
  * Returns True on success. Requires no parameters.
  */
case object DeleteWebhook extends JsonRequest[Boolean]