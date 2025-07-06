package com.bot4s.telegram.methods

import com.bot4s.telegram.models.InlineQueryResult
import com.bot4s.telegram.models.InlineQueryResultsButton
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to send answers to an inline query. On success, True is returned.
 * No more than 50 results per query are allowed.
 *
 * @param inlineQueryId  String Unique identifier for the answered query
 * @param results        Array of InlineQueryResult A JSON-serialized array of results for the inline query
 * @param cacheTime      Integer Optional The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to 300.
 * @param isPersonal     Boolean Optional Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
 * @param nextOffset     String Optional Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don't support pagination. Offset length can't exceed 64 bytes.
 * @param button         A JSON-serialized object describing a button to be shown above inline query results
 */
case class AnswerInlineQuery(
  inlineQueryId: String,
  results: Seq[InlineQueryResult],
  cacheTime: Option[Int] = None,
  isPersonal: Option[Boolean] = None,
  nextOffset: Option[String] = None,
  button: Option[InlineQueryResultsButton] = None
) extends JsonRequest[Boolean]

object AnswerInlineQuery {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[AnswerInlineQuery] = deriveConfiguredEncoder
}
