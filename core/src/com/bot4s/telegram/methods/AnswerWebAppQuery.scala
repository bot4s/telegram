package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ InlineQueryResult, SentWebAppMessage }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set the result of an interaction with a Web App and send a corresponding message on behalf of the user to the chat from which the query originated. On success, a SentWebAppMessage object is returned.
 *
 * @param webAppQueryId   Unique identifier for the query to be answered
 * @param result           	A JSON-serialized object describing the message to be sent
 */
case class AnswerWebAppQuery(
  webAppQueryId: String,
  result: Option[InlineQueryResult] = None
) extends JsonRequest[SentWebAppMessage]

object AnswerWebAppQuery {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[AnswerWebAppQuery] = deriveConfiguredEncoder
}
