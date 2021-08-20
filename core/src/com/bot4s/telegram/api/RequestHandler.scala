package com.bot4s.telegram.api

import java.util.UUID

import cats.MonadError
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.monadError._
import com.bot4s.telegram.methods._
import io.circe.{ Decoder, Encoder }
import com.typesafe.scalalogging.StrictLogging

import com.bot4s.telegram.marshalling._

abstract class RequestHandler[F[_]](implicit monadError: MonadError[F, Throwable]) extends StrictLogging {

  def sendRequest[R, T <: Request[_ /* R */ ]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): F[R]

  /**
   * Spawns a type-safe request.
   *
   * @param request
   * @tparam R Request's expected result type
   * @return The request result wrapped in a Future (async)
   */
  def apply[R](request: Request[R]): F[R] =
    for {
      uuid <- monadError.pure {
                val uuid = UUID.randomUUID()
                logger.trace("REQUEST {} {}", uuid, request)
                uuid
              }
      result <- monadError
                  .attempt(sendRequestInternal(request))
                  .flatTap({
                    case Right(response) =>
                      monadError.pure(logger.trace("RESPONSE {} {}", uuid, response))
                    case Left(e) =>
                      monadError.pure(logger.error("RESPONSE {} {}", uuid, e))
                  })
                  .rethrow
    } yield result

  private def sendRequestInternal[R](request: Request[R]): F[R] =
    request match {
      // Pure JSON requests
      case s: AnswerCallbackQuery             => sendRequest[R, AnswerCallbackQuery](s)
      case s: AnswerInlineQuery               => sendRequest[R, AnswerInlineQuery](s)
      case s: AnswerPreCheckoutQuery          => sendRequest[R, AnswerPreCheckoutQuery](s)
      case s: AnswerShippingQuery             => sendRequest[R, AnswerShippingQuery](s)
      case s: DeleteChatPhoto                 => sendRequest[R, DeleteChatPhoto](s)
      case s: DeleteChatStickerSet            => sendRequest[R, DeleteChatStickerSet](s)
      case s: DeleteMessage                   => sendRequest[R, DeleteMessage](s)
      case s: DeleteStickerFromSet            => sendRequest[R, DeleteStickerFromSet](s)
      case s: DeleteWebhook.type              => sendRequest[R, DeleteWebhook.type](s)
      case s: EditMessageCaption              => sendRequest[R, EditMessageCaption](s)
      case s: EditMessageLiveLocation         => sendRequest[R, EditMessageLiveLocation](s)
      case s: EditMessageReplyMarkup          => sendRequest[R, EditMessageReplyMarkup](s)
      case s: EditMessageText                 => sendRequest[R, EditMessageText](s)
      case s: ExportChatInviteLink            => sendRequest[R, ExportChatInviteLink](s)
      case s: ForwardMessage                  => sendRequest[R, ForwardMessage](s)
      case s: GetChat                         => sendRequest[R, GetChat](s)
      case s: GetChatAdministrators           => sendRequest[R, GetChatAdministrators](s)
      case s: GetChatMember                   => sendRequest[R, GetChatMember](s)
      case s: GetChatMemberCount              => sendRequest[R, GetChatMemberCount](s)
      case s: GetChatMembersCount             => sendRequest[R, GetChatMembersCount](s)
      case s: GetFile                         => sendRequest[R, GetFile](s)
      case s: GetGameHighScores               => sendRequest[R, GetGameHighScores](s)
      case s: GetMe.type                      => sendRequest[R, GetMe.type](s)
      case s: GetStickerSet                   => sendRequest[R, GetStickerSet](s)
      case s: GetUpdates                      => sendRequest[R, GetUpdates](s)
      case s: GetUserProfilePhotos            => sendRequest[R, GetUserProfilePhotos](s)
      case s: GetWebhookInfo.type             => sendRequest[R, GetWebhookInfo.type](s)
      case s: BanChatMember                   => sendRequest[R, BanChatMember](s)
      case s: KickChatMember                  => sendRequest[R, KickChatMember](s)
      case s: LeaveChat                       => sendRequest[R, LeaveChat](s)
      case s: PinChatMessage                  => sendRequest[R, PinChatMessage](s)
      case s: PromoteChatMember               => sendRequest[R, PromoteChatMember](s)
      case s: RestrictChatMember              => sendRequest[R, RestrictChatMember](s)
      case s: SetMyCommands                   => sendRequest[R, SetMyCommands](s)
      case s: GetMyCommands                   => sendRequest[R, GetMyCommands](s)
      case s: DeleteMyCommands                => sendRequest[R, DeleteMyCommands](s)
      case s: SendChatAction                  => sendRequest[R, SendChatAction](s)
      case s: SendContact                     => sendRequest[R, SendContact](s)
      case s: SendGame                        => sendRequest[R, SendGame](s)
      case s: SendInvoice                     => sendRequest[R, SendInvoice](s)
      case s: SendLocation                    => sendRequest[R, SendLocation](s)
      case s: SendMessage                     => sendRequest[R, SendMessage](s)
      case s: SendPoll                        => sendRequest[R, SendPoll](s)
      case s: SendVenue                       => sendRequest[R, SendVenue](s)
      case s: SetChatDescription              => sendRequest[R, SetChatDescription](s)
      case s: SetChatAdministratorCustomTitle => sendRequest[R, SetChatAdministratorCustomTitle](s)
      case s: SetChatPermissions              => sendRequest[R, SetChatPermissions](s)
      case s: SetChatStickerSet               => sendRequest[R, SetChatStickerSet](s)
      case s: SetChatTitle                    => sendRequest[R, SetChatTitle](s)
      case s: SetGameScore                    => sendRequest[R, SetGameScore](s)
      case s: SetStickerPositionInSet         => sendRequest[R, SetStickerPositionInSet](s)
      case s: StopMessageLiveLocation         => sendRequest[R, StopMessageLiveLocation](s)
      case s: StopPoll                        => sendRequest[R, StopPoll](s)
      case s: UnbanChatMember                 => sendRequest[R, UnbanChatMember](s)
      case s: UnpinChatMessage                => sendRequest[R, UnpinChatMessage](s)

      // Multipart requests
      case s: AddStickerToSet     => sendRequest[R, AddStickerToSet](s)
      case s: CreateNewStickerSet => sendRequest[R, CreateNewStickerSet](s)
      case s: SendAnimation       => sendRequest[R, SendAnimation](s)
      case s: SendAudio           => sendRequest[R, SendAudio](s)
      case s: SendDocument        => sendRequest[R, SendDocument](s)
      case s: SendMediaGroup      => sendRequest[R, SendMediaGroup](s)
      case s: SendPhoto           => sendRequest[R, SendPhoto](s)
      case s: SendSticker         => sendRequest[R, SendSticker](s)
      case s: SendVideo           => sendRequest[R, SendVideo](s)
      case s: SendVideoNote       => sendRequest[R, SendVideoNote](s)
      case s: SendVoice           => sendRequest[R, SendVoice](s)
      case s: SetChatPhoto        => sendRequest[R, SetChatPhoto](s)
      case s: SetWebhook          => sendRequest[R, SetWebhook](s)
      case s: UploadStickerFile   => sendRequest[R, UploadStickerFile](s)
    }

  protected def processApiResponse[R](response: Response[R]): R = response match {
    case Response(true, Some(result), _, _, _) => result
    case Response(false, _, description, Some(errorCode), parameters) =>
      throw TelegramApiException(
        description.getOrElse("Unexpected/invalid/empty response"),
        errorCode,
        None,
        parameters
      )

    case other =>
      throw new RuntimeException(s"Unexpected API response: $other")
  }
}
