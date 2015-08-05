trait Webhooks {
  this: TelegramBotAPI =>
  /**
   * setWebhook
   *
   * Use this method to specify a url and receive incoming updates via an outgoing webhook. Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a reasonable amount of attempts.
   * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. www.example.com/<token>. Since nobody else knows your bot‘s token, you can be pretty sure it’s us.
   * Parameters 	Type 	Required 	Description
   * url 	String 	Optional 	HTTPS url to send updates to. Use an empty string to remove webhook integration
   *   Notes
   *     1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
   *     2. We currently do not support self-signed certificates.
   *     3. Ports currently supported for Webhooks: 443, 80, 88, 8443.
   */
  def setWebhook(url: Option[String]): Unit = {
    getJson("setWebhook",
      "url" -> url)
  }
}