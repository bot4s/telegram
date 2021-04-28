package com.bot4s.telegram.api.declarative

import cats.instances.list._
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.syntax.traverse._
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.models.{ PreCheckoutQuery, ShippingQuery }

import scala.collection.mutable

/**
 * Declarative interface for processing payments.
 * See [[https://core.telegram.org/bots/payments]].
 */
trait Payments[F[_]] extends BotBase[F] {

  private val shippingQueryActions    = mutable.ArrayBuffer[Action[F, ShippingQuery]]()
  private val preCheckoutQueryActions = mutable.ArrayBuffer[Action[F, PreCheckoutQuery]]()

  /**
   * Executes 'action' for every shipping query.
   */
  def onShippingQuery(action: Action[F, ShippingQuery]): Unit =
    shippingQueryActions += action

  /**
   * Executes 'action' for every pre-checkout query.
   */
  def onPreCheckoutQuery(action: Action[F, PreCheckoutQuery]): Unit =
    preCheckoutQueryActions += action

  override def receiveShippingQuery(shippingQuery: ShippingQuery): F[Unit] =
    for {
      _ <- shippingQueryActions.toList.traverse(action => action(shippingQuery))
      _ <- super.receiveShippingQuery(shippingQuery)
    } yield ()

  override def receivePreCheckoutQuery(preCheckoutQuery: PreCheckoutQuery): F[Unit] =
    for {
      _ <- preCheckoutQueryActions.toList.traverse(action => action(preCheckoutQuery))
      _ <- super.receivePreCheckoutQuery(preCheckoutQuery)
    } yield ()
}
