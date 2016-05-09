package info.mukel.telegram.bots.v2.methods

import info.mukel.telegram.bots.v2.model.InputFile

/** setWebhook
  *
  * Use this method to specify a url and receive incoming updates via an outgoing webhook.
  * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update.
  * In case of an unsuccessful request, we will give up after a reasonable amount of attempts.
  * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. https://www.example.com/<token>.
  * Since nobody else knows your bot‘s token, you can be pretty sure it’s us.
  *
  * @param url          String	Optional	HTTPS url to send updates to. Use an empty string to remove webhook integration
  * @param certificate  InputFile	Optional	Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide for details.
  *
  * Notes
  *   1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
  *   2. To use a self-signed certificate, you need to upload your public key certificate using certificate parameter. Please upload as InputFile, sending a String will not work.
  *   3. Ports currently supported for Webhooks: 443, 80, 88, 8443.
  */
case class SetWebhook(
                     url         : Option[String] = None,
                     certificate : Option[InputFile] = None
                     ) extends ApiRequestJson[Boolean]

