package info.mukel.telegrambot4s.models

/**
  * Provides grouped update types to filter updates (e.g. message related, payments related).
  */
object UpdateType extends Enumeration {
  type UpdateType = Value
  val Message,
  EditedMessage,
  ChannelPost,
  EditedChannelPost,
  InlineQuery,
  ChosenInlineResult,
  CallbackQuery,
  ShippingQuery,
  PreCheckoutQuery = Value

  object Filters {
    val MessageUpdates: Seq[UpdateType] = Seq(Message, EditedMessage)
    val ChannelUpdates: Seq[UpdateType] = Seq(ChannelPost, EditedChannelPost)
    val InlineUpdates: Seq[UpdateType] = Seq(InlineQuery, ChosenInlineResult)
    val CallbackUpdates: Seq[UpdateType] = Seq(CallbackQuery)
    val PaymentUpdates: Seq[UpdateType] = Seq(ShippingQuery, PreCheckoutQuery)

    val AllUpdates: Seq[UpdateType] = UpdateType.values.toSeq
  }

}
