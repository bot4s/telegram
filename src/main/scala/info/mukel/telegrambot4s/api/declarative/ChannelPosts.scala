package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.models.Message

import scala.collection.mutable

/**
  * Declarative helpers for processing channel posts.
  */
trait ChannelPosts extends BotBase {

  private val channelPostActions = mutable.ArrayBuffer[ChannelPostAction]()
  private val editedChannelPostActions = mutable.ArrayBuffer[ChannelPostAction]()

  abstract override def receiveChannelPost(msg: Message): Unit = {
    for (action <- channelPostActions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveMessage(msg)
  }

  abstract override def receiveEditedChannelPost(msg: Message): Unit = {
    for (action <- editedChannelPostActions)
      action(msg)

    // Fallback to upper level to preserve trait stack-ability
    super.receiveMessage(msg)
  }

  /**
    * Filter channel posts and triggers custom actions.
    *
    * @param filter Message predicate, should not have side effects; every incoming message will be tested,
    *               it must be as cheap as possible.
    *
    * @param action Action to perform if the channel post pass the filter.
    */
  def whenChannelPost(filter: ChannelPostFilter)(action: ChannelPostAction): Unit = {
    channelPostActions += wrapFilteredAction(filter, action)
  }

  /**
    * Filter edited channel posts and triggers custom action.
    *
    * @param filter Message predicate, should not have side effects; every incoming message will be tested,
    *               it must be as cheap as possible.
    *
    * @param action Action to perform if the channel post pass the filter.
    */
  def whenEditedChannelPost(filter: ChannelPostFilter)(action: ChannelPostAction): Unit = {
    editedChannelPostActions += wrapFilteredAction(filter, action)
  }

  /**
    * Executes `action` for every channel post.
    */
  def onChannelPost(action: ChannelPostAction): Unit = {
    channelPostActions += action
  }

  /**
    * Executes `action` for every incoming edited channel post.
    */
  def onEditedChannelPost(action: ChannelPostAction): Unit = {
    editedChannelPostActions += action
  }
}
