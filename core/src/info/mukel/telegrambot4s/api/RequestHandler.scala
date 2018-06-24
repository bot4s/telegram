package info.mukel.telegrambot4s.api

import java.util.UUID

import scala.concurrent.ExecutionContext.Implicits.global
import info.mukel.telegrambot4s.methods._
import io.circe.{Decoder, Encoder}
import slogging.StrictLogging

import scala.concurrent.Future
import scala.util.{Failure, Success}
trait RequestHandler extends StrictLogging {

  import info.mukel.telegrambot4s.marshalling.CirceMarshaller._

  def sendRequest[R, T <: ApiRequest[_ /* R */]](request: T)(implicit encT: Encoder[T], decR: Decoder[R]): Future[R]

  /** Spawns a type-safe request.
    *
    * @param request
    * @tparam R Request's expected result type
    * @return The request result wrapped in a Future (async)
    */
  def apply[R](request: ApiRequest[R]): Future[R] = {
    val uuid = UUID.randomUUID()
    logger.trace("REQUEST {} {}", uuid, request)
    val f: Future[R] = request match {
      // Pure JSON requests
      case s: AnswerCallbackQuery => sendRequest[R, AnswerCallbackQuery](s)
      case s: AnswerInlineQuery => sendRequest[R, AnswerInlineQuery](s)
      case s: AnswerPreCheckoutQuery => sendRequest[R, AnswerPreCheckoutQuery](s)
      case s: AnswerShippingQuery => sendRequest[R, AnswerShippingQuery](s)
      case s: DeleteChatPhoto => sendRequest[R, DeleteChatPhoto](s)
      case s: DeleteChatStickerSet => sendRequest[R, DeleteChatStickerSet](s)
      case s: DeleteMessage => sendRequest[R, DeleteMessage](s)
      case s: DeleteStickerFromSet => sendRequest[R, DeleteStickerFromSet](s)
      case s: DeleteWebhook.type => sendRequest[R, DeleteWebhook.type](s)
      case s: EditMessageCaption => sendRequest[R, EditMessageCaption](s)
      case s: EditMessageLiveLocation => sendRequest[R, EditMessageLiveLocation](s)
      case s: EditMessageReplyMarkup => sendRequest[R, EditMessageReplyMarkup](s)
      case s: EditMessageText => sendRequest[R, EditMessageText](s)
      case s: ExportChatInviteLink => sendRequest[R, ExportChatInviteLink](s)
      case s: ForwardMessage => sendRequest[R, ForwardMessage](s)
      case s: GetChat => sendRequest[R, GetChat](s)
      case s: GetChatAdministrators => sendRequest[R, GetChatAdministrators](s)
      case s: GetChatMember => sendRequest[R, GetChatMember](s)
      case s: GetChatMembersCount => sendRequest[R, GetChatMembersCount](s)
      case s: GetFile => sendRequest[R, GetFile](s)
      case s: GetGameHighScores => sendRequest[R, GetGameHighScores](s)
      case s: GetMe.type => sendRequest[R, GetMe.type](s)
      case s: GetStickerSet => sendRequest[R, GetStickerSet](s)
      case s: GetUpdates => sendRequest[R, GetUpdates](s)
      case s: GetUserProfilePhotos => sendRequest[R, GetUserProfilePhotos](s)
      case s: GetWebhookInfo.type => sendRequest[R, GetWebhookInfo.type](s)
      case s: KickChatMember => sendRequest[R, KickChatMember](s)
      case s: LeaveChat => sendRequest[R, LeaveChat](s)
      case s: PinChatMessage => sendRequest[R, PinChatMessage](s)
      case s: PromoteChatMember => sendRequest[R, PromoteChatMember](s)
      case s: RestrictChatMember => sendRequest[R, RestrictChatMember](s)
      case s: SendChatAction => sendRequest[R, SendChatAction](s)
      case s: SendContact => sendRequest[R, SendContact](s)
      case s: SendGame => sendRequest[R, SendGame](s)
      case s: SendInvoice => sendRequest[R, SendInvoice](s)
      case s: SendLocation => sendRequest[R, SendLocation](s)
      case s: SendMessage => sendRequest[R, SendMessage](s)
      case s: SendVenue => sendRequest[R, SendVenue](s)
      case s: SetChatDescription => sendRequest[R, SetChatDescription](s)
      case s: SetChatStickerSet => sendRequest[R, SetChatStickerSet](s)
      case s: SetChatTitle => sendRequest[R, SetChatTitle](s)
      case s: SetGameScore => sendRequest[R, SetGameScore](s)
      case s: SetStickerPositionInSet => sendRequest[R, SetStickerPositionInSet](s)
      case s: StopMessageLiveLocation => sendRequest[R, StopMessageLiveLocation](s)
      case s: UnbanChatMember => sendRequest[R, UnbanChatMember](s)
      case s: UnpinChatMessage => sendRequest[R, UnpinChatMessage](s)

      // Multipart requests
      case s: AddStickerToSet => sendRequest[R, AddStickerToSet](s)
      case s: CreateNewStickerSet => sendRequest[R, CreateNewStickerSet](s)
      case s: SendAudio => sendRequest[R, SendAudio](s)
      case s: SendDocument => sendRequest[R, SendDocument](s)
      case s: SendMediaGroup => sendRequest[R, SendMediaGroup](s)
      case s: SendPhoto => sendRequest[R, SendPhoto](s)
      case s: SendSticker => sendRequest[R, SendSticker](s)
      case s: SendVideo => sendRequest[R, SendVideo](s)
      case s: SendVideoNote => sendRequest[R, SendVideoNote](s)
      case s: SendVoice => sendRequest[R, SendVoice](s)
      case s: SetChatPhoto => sendRequest[R, SetChatPhoto](s)
      case s: SetWebhook => sendRequest[R, SetWebhook](s)
      case s: UploadStickerFile => sendRequest[R, UploadStickerFile](s)
    }

    f.onComplete {
      case Success(response) => logger.trace("RESPONSE {} {}", uuid, response)
      case Failure(e) => logger.error("RESPONSE {} {}", uuid, e)
    }

    f
  }

  protected def processApiResponse[R](response: ApiResponse[R]): R = response match {
    case ApiResponse(true, Some(result), _, _, _) => result
    case ApiResponse(false, _, description, Some(errorCode), parameters) =>
      throw TelegramApiException(description.getOrElse("Unexpected/invalid/empty response"), errorCode, None, parameters)

    case other =>
      throw new RuntimeException(s"Unexpected API response: $other")
  }
}
