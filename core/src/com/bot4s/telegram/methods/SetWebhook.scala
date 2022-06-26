package com.bot4s.telegram.methods

import com.bot4s.telegram.models.InputFile
import com.bot4s.telegram.models.UpdateType.UpdateType

/**
 * Use this method to specify a url and receive incoming updates via an outgoing webhook.
 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update.
 * In case of an unsuccessful request, we will give up after a reasonable amount of attempts. Returns true.
 * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. https://www.example.com/<token>.
 * Since nobody else knows your bot‘s token, you can be pretty sure it’s us.
 *
 * @param url                String Yes HTTPS url to send updates to. Use an empty string to remove webhook integration
 * @param certificate        InputFile Optional Upload your public key certificate so that the root certificate in use can be checked. See our self-signed guide for details.
 * @param ipAddress          String Optional The fixed IP address which will be used to send webhook requests instead of the IP address resolved through DNS
 * @param maxConnections     Integer Optional Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery, 1-100. Defaults to 40. Use lower values to limit the load on your bot‘s server, and higher values to increase your bot’s throughput.
 * @param allowedUpdates     Array of String Optional List the types of updates you want your bot to receive.
 *                           For example, specify [“message”, “edited_channel_post”, “callback_query”] to only receive updates of these types.
 *                           See Update for a complete list of available update types.
 *                           Specify an empty list to receive all updates regardless of type (default).
 *                           If not specified, the previous setting will be used.
 * @param dropPendingUpdates Boolean Optional Pass True to drop all pending updates.
 * @param secretToken        String Optional A secret token to be sent in a header “X-Telegram-Bot-Api-Secret-Token” in every webhook request, 1-256 characters.
 *                            Only characters A-Z, a-z, 0-9, _ and - are allowed.
 *                            The header is useful to ensure that the request comes from a webhook set by you.
 *
 * Please note that this parameter doesn't affect updates created before the call to the setWebhook, so unwanted updates may be received for a short period of time.
 *
 * '''Notes'''
 *   1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
 *   2. To use a self-signed certificate, you need to upload your public key certificate using certificate parameter.
 *   3. Ports currently supported for Webhooks: 443, 80, 88, 8443.
 *
 * NEW! If you're having any trouble setting up webhooks, please check out this [[https://core.telegram.org/bots/webhooks amazing guide to Webhooks]].
 */
case class SetWebhook(
  url: String,
  certificate: Option[InputFile] = None,
  maxConnections: Option[Int] = None,
  allowedUpdates: Option[Seq[UpdateType]] = None,
  ipAddress: Option[String] = None,
  dropPendingUpdates: Option[Boolean] = None,
  secretToken: Option[String] = None
) extends MultipartRequest[Boolean] {
  override def getFiles: List[(String, InputFile)] = certificate.map("certificate" -> _).toList
}
