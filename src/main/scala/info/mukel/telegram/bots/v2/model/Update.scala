package info.mukel.telegram.bots.v2.model

/** Update
  *
  * This object represents an incoming update.
  *
  * @param updateId           The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
  * @param message            Optional New incoming message of any kind — text, photo, sticker, etc.
  * @param inlineQuery        InlineQuery	Optional New incoming inline query
  * @param chosenInlineResult ChosenInlineResult	Optional The result of a inline query that was chosen by a user and sent to their chat partner
  * @param callbackQuery      Optional New incoming callback query
  */
case class Update(
                   updateId           : Long,
                   message            : Option[Message] = None,
                   inlineQuery        : Option[InlineQuery] = None,
                   chosenInlineResult : Option[ChosenInlineResult] = None,
                   callbackQuery      : Option[CallbackQuery] = None
                 )
