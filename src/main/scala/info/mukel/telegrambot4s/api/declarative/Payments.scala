package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.{PreCheckoutQuery, ShippingQuery}

import scala.collection.mutable

/**
  * Declarative interface for processing payments.
  * See [[https://core.telegram.org/bots/payments]].
  */
trait Payments extends BotBase {

  private val shippingQueryActions = mutable.ArrayBuffer[ShippingQueryAction]()
  private val preCheckoutQueryActions = mutable.ArrayBuffer[PreCheckoutQueryAction]()

  /**
    * Executes 'action' for every shipping query.
    */
  def onShippingQuery(action: ShippingQueryAction): Unit = {
    shippingQueryActions += action
  }

  /**
    * Executes 'action' for every pre-checkout query.
    */
  def onPreCheckoutQuery(action: PreCheckoutQueryAction): Unit = {
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
