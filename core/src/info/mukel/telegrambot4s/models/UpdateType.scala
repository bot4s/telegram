package info.mukel.telegrambot4s.models

/**
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
    val MessageUpdates: Seq[UpdateType] = Seq(Message, EditedMessage)
    val ChannelUpdates: Seq[UpdateType] = Seq(ChannelPost, EditedChannelPost)
    val InlineUpdates: Seq[UpdateType] = Seq(InlineQuery, ChosenInlineResult)
    val CallbackUpdates: Seq[UpdateType] = Seq(CallbackQuery)
    val PaymentUpdates: Seq[UpdateType] = Seq(ShippingQuery, PreCheckoutQuery)

    val AllUpdates: Seq[UpdateType] = UpdateType.values.toSeq
  }
}
