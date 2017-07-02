package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.Update

import scala.collection.mutable

/**
  * Declarative helpers for processing updates.
  */
trait Updates extends BotBase {

  private val updateActions = mutable.ArrayBuffer[UpdateAction]()

  /**
    * Executes `action` for every update.
    */
  def onUpdate(action: UpdateAction): Unit = {
    updateActions += action
  }

  abstract override def receiveUpdate(update: Update): Unit = {
    for (action <- updateActions)
      action(update)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveUpdate(update)
  }
}
