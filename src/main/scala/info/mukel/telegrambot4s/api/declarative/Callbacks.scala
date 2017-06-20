package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.api.BotBase
import info.mukel.telegrambot4s.methods.AnswerCallbackQuery
import info.mukel.telegrambot4s.models.CallbackQuery

import scala.collection.mutable
import scala.concurrent.Future

/**
  * Declarative interface for callbacks; allows filtering callback-query events by tag.
  */
trait Callbacks extends BotBase {

  private val actions = mutable.ArrayBuffer[CallbackQueryAction]()

  /** Filters callbacks based on a tag (to avoid collision).
    * The tag is stripped from the CallbackQuery object when passed to the handler.
    *
    * @param tag Tag
    * @param action Handler to process the filtered callback query.
    */
  def onCallbackWithTag(tag: String)(action: CallbackQueryAction): Unit = {
    whenCallbackQuery(_.data.exists(_.startsWith(tag))) {
      cbq =>
        untag(tag)(action)(cbq)
    }
  }

  /** Generic callbacks filtering (to avoid collision).
    *
    * @param filter A filter should not have side effects, and should be fast (no DB requests).
    * @param action Method to process the filtered callback query.
    */
  def whenCallbackQuery(filter: CallbackQueryFilter)(action: CallbackQueryAction): Unit = {
    actions += wrapFilteredAction(filter, action)
  }

  /**
    * Executes 'action' for every incoming callback query.
    */
  def onCallbackQuery(action: CallbackQueryAction): Unit = {
    actions += action
  }

  abstract override def receiveCallbackQuery(callbackQuery: CallbackQuery): Unit = {
    for (action <- actions)
      action(callbackQuery)

    // Preserve trait stack-ability.
    super.receiveCallbackQuery(callbackQuery)
  }

  /**
    * Send a response for a callback query.
    * Callback queries must ack-ed e.g. ackCallback(), even if no response is sent.
    *
    * @param text       Text of the notification. If not specified, nothing will be shown to the user
    * @param showAlert  If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
    * @param url        URL that will be opened by the user's client.
    *                   If you have created a Game and accepted the conditions via @Botfather,
    *                   specify the URL that opens your game - note that this will only work if the query comes
    *                   from a callback_game button.
    * @param cacheTime  The maximum amount of time in seconds that the result of the callback query may be cached client-side.
    *                   Telegram apps will support caching starting in version 3.14. Defaults to 0.
    *
    * @return A future containing the result of the AnswerCallbackQuery request.
    */
  def ackCallback(text         : Option[String] = None,
                  showAlert    : Option[Boolean] = None,
                  url          : Option[String] = None,
                  cacheTime    : Option[Int] = None)
                 (implicit callbackQuery: CallbackQuery): Future[Boolean] = {
    request(AnswerCallbackQuery(callbackQuery.id, text, showAlert, url, cacheTime))
  }

 /**
   * Helper to tag 'callbackData' in inline markups.
   * Usage:
   *
   * {{{
   *   def tag = prefixTag("MY_TAG") _
   *
   *   ... callbackData = tag("some data")
   * }}}
   */
  def prefixTag(tag: String)(s: String): String = tag + s

  private def untag(tag: String)(action: CallbackQueryAction)(implicit cbq: CallbackQuery) = {
    action(cbq.copy(data = cbq.data.map(_.stripPrefix(tag))))
  }
}
