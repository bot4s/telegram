package info.mukel.telegrambot4s.methods

/** Use this method to remove webhook integration if you decide to switch back to getUpdates.
  * Returns True on success. Requires no parameters.
  */
case object DeleteWebhook extends ApiRequestJson[Boolean]