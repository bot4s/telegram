package com.bot4s.telegram.api.declarative

import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.models.{PreCheckoutQuery, ShippingQuery}

import scala.collection.mutable

/**
  * Declarative interface for processing payments.
  * See [[https://core.telegram.org/bots/payments]].
  */
trait Payments extends BotBase {

  private val shippingQueryActions = mutable.ArrayBuffer[Action[ShippingQuery]]()
  private val preCheckoutQueryActions = mutable.ArrayBuffer[Action[PreCheckoutQuery]]()

  /**
    * Executes 'action' for every shipping query.
    */
  def onShippingQuery(action: Action[ShippingQuery]): Unit = {
    shippingQueryActions += action
  }

  /**
    * Executes 'action' for every pre-checkout query.
    */
  def onPreCheckoutQuery(action: Action[PreCheckoutQuery]): Unit = {
    preCheckoutQueryActions += action
  }

  abstract override def receiveShippingQuery(shippingQuery: ShippingQuery): Unit = {
    for (action <- shippingQueryActions)
      action(shippingQuery)

    // Preserve trait stack-ability.
    super.receiveShippingQuery(shippingQuery)
  }

  abstract override def receivePreCheckoutQuery(preCheckoutQuery: PreCheckoutQuery): Unit = {
    for (action <- preCheckoutQueryActions)
      action(preCheckoutQuery)

    // Preserve trait stack-ability.
    super.receivePreCheckoutQuery(preCheckoutQuery)
  }
}
