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

  /** Generic filtering for shipping queries.
    *
    * @param filter A filter should not have side effects.
    * @param action Method to process the filtered inline query.
    */
  def whenShippingQuery(filter: ShippingQueryFilter)(action: ShippingQueryAction): Unit = {
    shippingQueryActions += wrapFilteredAction(filter, action)
  }

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

  /** Generic filtering for pre-checkout queries.
    *
    * @param filter A filter should not have side effects.
    * @param action Action to process the filtered result.
    */
  def whenPreCheckoutQuery(filter: PreCheckoutQueryFilter)(action: PreCheckoutQueryAction): Unit = {
    preCheckoutQueryActions += wrapFilteredAction(filter, action)
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
