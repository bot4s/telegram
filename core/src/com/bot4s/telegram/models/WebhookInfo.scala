package com.bot4s.telegram.models

/**
 * Contains information about the current status of a webhook.
 *
 * @param url                   Webhook URL, may be empty if webhook is not set up
 * @param hasCustomCertificate  True, if a custom certificate was provided for webhook certificate checks
 * @param pendingUpdateCount    Number of updates awaiting delivery
 * @param lastErrorDate         Optional. Unix time for the most recent error that happened when trying to deliver an update via webhook
 * @param lastErrorMessage      Optional. Error message in human-readable format for the most recent error that happened when trying to deliver an update via webhook
 */
case class WebhookInfo(
  url: String,
  hasCustomCertificate: Boolean,
  pendingUpdateCount: Int,
  lastErrorDate: Option[Int] = None,
  lastErrorMessage: Option[String] = None
)
