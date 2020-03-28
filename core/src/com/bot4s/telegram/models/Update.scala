package com.bot4s.telegram.models

/** This object represents an incoming update.
  * At most one of the optional parameters can be present in any given update.
  *
  * @param updateId            The update's unique identifier.
  *                            Update identifiers start from a certain positive number and increase sequentially.
  *                            This ID becomes especially handy if you're using Webhooks, since it allows you to ignore
  *                            repeated updates or to restore the correct update sequence, should they get out of order.
  * @param message             Optional New incoming message of any kind - text, photo, sticker, etc.
  * @param editedMessage       Optional. New version of a message that is known to the bot and was edited
  * @param channelPost         Message Optional. New incoming channel post of any kind - text, photo, sticker, etc.
  * @param editedChannelPost   Message Optional. New version of a channel post that is known to the bot and was edited
  * @param inlineQuery         InlineQuery Optional New incoming inline query
  * @param chosenInlineResult  ChosenInlineResult Optional The result of a inline query that was chosen by a user and sent to their chat partner
  * @param callbackQuery       Optional New incoming callback query
  * @param shippingQuery       ShippingQuery Optional. New incoming shipping query. Only for invoices with flexible price
  * @param preCheckoutQuery    PreCheckoutQuery Optional. New incoming pre-checkout query. Contains full information about checkout
  * @param poll                Poll Optional. New poll state. Bots receive only updates about polls, which are sent or stopped by the bot
  */
case class Update(updateId: Long,
                  message: Option[Message] = None,
                  editedMessage: Option[Message] = None,
                  channelPost: Option[Message] = None,
                  editedChannelPost: Option[Message] = None,
                  inlineQuery: Option[InlineQuery] = None,
                  chosenInlineResult: Option[ChosenInlineResult] = None,
                  callbackQuery: Option[CallbackQuery] = None,
                  shippingQuery: Option[ShippingQuery] = None,
                  preCheckoutQuery: Option[PreCheckoutQuery] = None,
                  poll: Option[Poll] = None) {

  require(
    Seq[Option[_]](
      message,
      editedMessage,
      channelPost,
      editedChannelPost,
      inlineQuery,
      chosenInlineResult,
      callbackQuery,
      shippingQuery,
      preCheckoutQuery,
      poll
    ).count(_.isDefined) == 1,
    "Exactly one of the optional fields should be used"
  )
}
