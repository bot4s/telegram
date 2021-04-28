package com.bot4s.telegram.api.declarative

import cats.instances.list._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.traverse._
import com.bot4s.telegram.api.BotBase
import com.bot4s.telegram.methods.AnswerInlineQuery
import com.bot4s.telegram.models.{ ChosenInlineResult, InlineQuery, InlineQueryResult }

import scala.collection.mutable

/**
 * Declarative interface for processing inline queries.
 */
trait InlineQueries[F[_]] extends BotBase[F] {

  private val inlineQueryActions        = mutable.ArrayBuffer[Action[F, InlineQuery]]()
  private val chosenInlineResultActions = mutable.ArrayBuffer[Action[F, ChosenInlineResult]]()

  /**
   * Executes 'action' for every inline query.
   */
  def onInlineQuery(action: Action[F, InlineQuery]): Unit =
    inlineQueryActions += action

  /**
   * Executes 'action' for every inline result.
   * * See [[https://core.telegram.org/bots/inline#collecting-feedback]]
   */
  def onChosenInlineResult(action: Action[F, ChosenInlineResult]): Unit =
    chosenInlineResultActions += action

  /**
   * Use this method to send answers to an inline query. On success, True is returned.
   * No more than 50 results per query are allowed.
   *
   * @param results            Array of InlineQueryResult A JSON-serialized array of results for the inline query
   * @param cacheTime          Integer Optional The maximum amount of time in seconds that the result of the inline query may be cached on the server. Defaults to 300.
   * @param isPersonal         Boolean Optional Pass True, if results may be cached on the server side only for the user that sent the query. By default, results may be returned to any user who sends the same query
   * @param nextOffset         String Optional Pass the offset that a client should send in the next query with the same text to receive more results. Pass an empty string if there are no more results or if you don't support pagination. Offset length can't exceed 64 bytes.
   * @param switchPmText       String Optional If passed, clients will display a button with specified text that switches the user to a private chat with the bot and sends the bot a start message with the parameter switch_pm_parameter
   * @param switchPmParameter  String Optional Parameter for the start message sent to the bot when user presses the switch buttonExample: An inline bot that sends YouTube videos can ask the user to connect the bot to their YouTube account to adapt search results accordingly. To do this, it displays a 'Connect your YouTube account' button above the results, or even before showing any. The user presses the button, switches to a private chat with the bot and, in doing so, passes a start parameter that instructs the bot to return an oauth link. Once done, the bot can offer a switch_inline button so that the user can easily return to the chat where they wanted to use the bot's inline capabilities.
   */
  def answerInlineQuery(
    results: Seq[InlineQueryResult],
    cacheTime: Option[Int] = None,
    isPersonal: Option[Boolean] = None,
    nextOffset: Option[String] = None,
    switchPmText: Option[String] = None,
    switchPmParameter: Option[String] = None
  )(implicit inlineQuery: InlineQuery): F[Boolean] =
    request(
      AnswerInlineQuery(inlineQuery.id, results, cacheTime, isPersonal, nextOffset, switchPmText, switchPmParameter)
    )

  override def receiveInlineQuery(inlineQuery: InlineQuery): F[Unit] =
    for {
      _ <- inlineQueryActions.toList.traverse(action => action(inlineQuery))
      _ <- super.receiveInlineQuery(inlineQuery)
    } yield ()

  override def receiveChosenInlineResult(chosenInlineResult: ChosenInlineResult): F[Unit] =
    for {
      _ <- chosenInlineResultActions.toList.traverse(action => action(chosenInlineResult))
      _ <- super.receiveChosenInlineResult(chosenInlineResult)
    } yield ()
}
