package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ InlineKeyboardMarkup, Message }
import com.bot4s.telegram.models.ChatId
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to edit live location messages sent by the bot or via the bot (for inline bots).
 * A location can be edited until its live_period expires or editing is explicitly disabled by a call to stopMessageLiveLocation.
 * On success, if the edited message was sent by the bot,
 * the edited Message is returned, otherwise True is returned.
 *
 * @param chatId           Integer or String Optional	Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param messageId        Integer	Optional Required if inline_message_id is not specified. Identifier of the sent message
 * @param inlineMessageId  String	Optional Required if chat_id and message_id are not specified. Identifier of the inline message
 * @param latitude         Float number Yes Latitude of new location
 * @param longitude        Float number Yes	Longitude of new location
 * @param replyMarkup      InlineKeyboardMarkup Optional A JSON-serialized object for a new inline keyboard.
 */
case class EditMessageLiveLocation(
  chatId: Option[ChatId] = None,
  messageId: Option[Int] = None,
  inlineMessageId: Option[Int] = None,
  latitude: Option[Double] = None,
  longitude: Option[Double] = None,
  replyMarkup: Option[InlineKeyboardMarkup] = None
) extends JsonRequest {
  type Response = Message Either Boolean
}

object EditMessageLiveLocation {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames
  implicit val editMessageLiveLocationEncoder: Encoder[EditMessageLiveLocation] =
    deriveConfiguredEncoder[EditMessageLiveLocation]
}
