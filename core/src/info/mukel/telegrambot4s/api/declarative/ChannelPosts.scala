package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.Message

import scala.collection.mutable

/**
  * Declarative helpers for processing channel posts.
  */
trait ChannelPosts extends BotBase {

  private val channelPostActions = mutable.ArrayBuffer[Action[Message]]()
  private val editedChannelPostActions = mutable.ArrayBuffer[Action[Message]]()

  /**
    * Executes `action` for every channel post.
    */
  def onChannelPost(action: Action[Message]): Unit = {
    channelPostActions += action
  }

  /**
    * Executes `action` for every incoming edited channel post.
    */
  def onEditedChannelPost(action: Action[Message]): Unit = {
    editedChannelPostActions += action
  }

  abstract override def receiveChannelPost(msg: Message): Unit = {
    for (action <- channelPostActions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability.
    super.receiveChannelPost(msg)
  }

  abstract override def receiveEditedChannelPost(msg: Message): Unit = {
    for (action <- editedChannelPostActions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability.
    super.receiveEditedChannelPost(msg)
  }
}
