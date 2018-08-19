package com.bot4s.telegram.api.declarative

import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.models.Update

import scala.collection.mutable

/**
  * Declarative helpers for processing updates.
  */
trait Updates extends BotBase {

  private val updateActions = mutable.ArrayBuffer[Action[Update]]()

  /**
    * Executes `action` for every update.
    */
  def onUpdate(action: Action[Update]): Unit = {
    updateActions += action
  }

  abstract override def receiveUpdate(update: Update): Unit = {
    for (action <- updateActions)
      action(update)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveUpdate(update)
  }
}
