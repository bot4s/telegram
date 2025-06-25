package com.bot4s.telegram.models

/**
 * Provides grouped update types to filter updates (e.g. message related, payments related).
 */
enum UpdateType:
  case Message, EditedMessage, ChannelPost, EditedChannelPost
  case InlineQuery, ChosenInlineResult, CallbackQuery
  case ShippingQuery, PreCheckoutQuery

object UpdateType:
  object Filters:
    val MessageUpdates: Seq[UpdateType]  = Seq(Message, EditedMessage)
    val ChannelUpdates: Seq[UpdateType]  = Seq(ChannelPost, EditedChannelPost)
    val InlineUpdates: Seq[UpdateType]   = Seq(InlineQuery, ChosenInlineResult)
    val CallbackUpdates: Seq[UpdateType] = Seq(CallbackQuery)
    val PaymentUpdates: Seq[UpdateType]  = Seq(ShippingQuery, PreCheckoutQuery)

    val AllUpdates: Seq[UpdateType] = UpdateType.values.toSeq
