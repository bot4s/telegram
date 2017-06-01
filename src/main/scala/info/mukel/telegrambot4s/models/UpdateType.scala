package info.mukel.telegrambot4s.models

/**
  * Different types of updates.
  * Provides grouped update types to filter updates (e.g. message related, payments related).
  */
object UpdateType extends Enumeration {
  type UpdateType = Value
  val Message = Value("message")
  val EditedMessage = Value("edited_message")

  val ChannelPost = Value("channel_post")
  val EditedChannelPost = Value("edited_channel_post")

  val InlineQuery = Value("inline_query")
  val ChosenInlineResult = Value("chosen_inline_result")

  val CallbackQuery = Value("callback_query")

  val ShippingQuery = Value("shipping_query")
  val PreCheckoutQuery = Value("pre_checkout_query")

  object Filters {
    val MessageUpdates = Seq(Message, EditedMessage)
    val ChannelUpdates = Seq(ChannelPost, EditedChannelPost)
    val InlineUpdates = Seq(InlineQuery, ChosenInlineResult)
    val CallbackUpdates = Seq(CallbackQuery)
    val PaymentUpdates = Seq(ShippingQuery, PreCheckoutQuery)

    val AllUpdates = UpdateType.values.toSeq
  }
}
