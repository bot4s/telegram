package com.bot4s.telegram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/**
 * Contains information about the current status of a webhook.
 *
 * @param url                           Webhook URL, may be empty if webhook is not set up
 * @param hasCustomCertificate          True, if a custom certificate was provided for webhook certificate checks
 * @param pendingUpdateCount            Number of updates awaiting delivery
 * @param ipAddress                     String Optional. Currently used webhook IP address
 * @param lastErrorDate                 Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook
 * @param lastErrorMessage              Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook
 * @param lastSynchronizationErrorDate  Optional. Unix time for the most recent error that happened when trying to synchronize the webhook with Telegram datacenter
 * @param maxConnections                Optional. Integer. Maximum allowed number of simultaneous HTTPS connections to the webhook for update delivery
 * @param allowedUpdates                Optional. Array of String. A list of update types the bot is subscribed to. Defaults to all update types except chat_member
 */
case class WebhookInfo(
  url: String,
  hasCustomCertificate: Boolean,
  pendingUpdateCount: Int,
  ipAddress: Option[String] = None,
  lastErrorDate: Option[Int] = None,
  lastErrorMessage: Option[String] = None,
  lastSynchronizationErrorDate: Option[Int] = None,
  maxConnections: Option[Int] = None,
  allowedUpdates: Option[Array[String]] = None
)

object WebhookInfo {
  implicit val circeDecoder: Decoder[WebhookInfo] = deriveDecoder[WebhookInfo]
}
