package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.Update

import scala.collection.mutable

/**
  * Declarative helpers for processing updates.
  */
trait Updates extends BotBase {

  private val updateActions = mutable.ArrayBuffer[UpdateAction]()

  abstract override def receiveUpdate(update: Update): Unit = {
    for (action <- updateActions)
      action(update)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveUpdate(update)
  }

  /**
    * Filter incoming updates and triggers custom actions.
    *
    * @param filter Predicate, should not have side effects; every update will be tested,
    *               it must be as cheap as possible.
    *
    * @param action Action to perform if the incoming update pass the filter.
    */
  def whenUpdate(filter: UpdateFilter)(action: UpdateAction): Unit = {
    updateActions += wrapFilteredAction(filter, action)
  }

  /**
    * Executes `action` for every update.
    */
  def onUpdate(action: UpdateAction): Unit = {
    updateActions += action
  }
}
