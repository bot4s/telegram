package com.bot4s.telegram.marshalling

import com.bot4s.telegram.methods._
import com.bot4s.telegram.methods.ChatAction.ChatAction
import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.models._
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.ChatType.ChatType
import com.bot4s.telegram.models.CountryCode.CountryCode
import com.bot4s.telegram.models.Currency.{Currency, TelegramCurrency}
import com.bot4s.telegram.models.MaskPositionType.MaskPositionType
import com.bot4s.telegram.models.MemberStatus.MemberStatus
import com.bot4s.telegram.models.MessageEntityType.MessageEntityType
import UpdateType.UpdateType
import com.bot4s.telegram.models._
import io.circe.Encoder
import io.circe.generic.extras._
import io.circe.generic.extras.auto._
import io.circe.generic.extras.semiauto._
import io.circe.syntax._

/** Circe marshalling borrowed/inspired from [[https://github.com/nikdon/telepooz]]
  */
trait CirceEncoders {

  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  // Models
  implicit val audioEncoder: Encoder[Audio] = deriveEncoder[Audio]
  implicit val callbackQueryEncoder: Encoder[CallbackQuery] = deriveEncoder[CallbackQuery]

  implicit val callbackGameEncoder: Encoder[CallbackGame] = deriveEncoder[CallbackGame]

  implicit val chatTypeEncoder: Encoder[ChatType] =
    Encoder[String].contramap[ChatType](e => CaseConversions.snakenize(e.toString))

  implicit val chatEncoder: Encoder[Chat] = deriveEncoder[Chat]

  implicit val chatMemberEncoder: Encoder[ChatMember] = deriveEncoder[ChatMember]

  implicit val contactEncoder: Encoder[Contact] = deriveEncoder[Contact]
  implicit val documentEncoder: Encoder[Document] = deriveEncoder[Document]
  implicit val fileEncoder: Encoder[File] = deriveEncoder[File]

  implicit val inlineKeyboardButtonEncoder: Encoder[InlineKeyboardButton] = deriveEncoder[InlineKeyboardButton]
  implicit val keyboardButtonEncoder: Encoder[KeyboardButton] = deriveEncoder[KeyboardButton]
  implicit val locationEncoder: Encoder[Location] = deriveEncoder[Location]

  implicit val gameHighScoreEncoder: Encoder[GameHighScore] = deriveEncoder[GameHighScore]
  implicit val animationEncoder: Encoder[Animation] = deriveEncoder[Animation]
  implicit val gameEncoder: Encoder[Game] = deriveEncoder[Game]

  implicit val messageEncoder: Encoder[Message] = deriveEncoder[Message]

  implicit val messageEntityTypeEncoder: Encoder[MessageEntityType] =
    Encoder[String].contramap[MessageEntityType](e ⇒ CaseConversions.snakenize(e.toString))

  implicit val messageEntityEncoder: Encoder[MessageEntity] = deriveEncoder[MessageEntity]

  implicit val parseModeEncoder: Encoder[ParseMode] =
    Encoder[String].contramap[ParseMode](e => CaseConversions.snakenize(e.toString))

  implicit val photoSizeEncoder: Encoder[PhotoSize] = deriveEncoder[PhotoSize]

  implicit val memberStatusEncoder: Encoder[MemberStatus] =
    Encoder[String].contramap(e => CaseConversions.snakenize(e.toString))

  implicit val replyMarkupEncoder: Encoder[ReplyMarkup] = Encoder.instance {
    case fr: ForceReply => fr.asJson
    case ikm: InlineKeyboardMarkup => ikm.asJson
    case rkr: ReplyKeyboardRemove => rkr.asJson
    case rkm: ReplyKeyboardMarkup => rkm.asJson
  }

  implicit val stickerEncoder: Encoder[Sticker] = deriveEncoder[Sticker]
  implicit val maskPositionEncoder: Encoder[MaskPosition] = deriveEncoder[MaskPosition]

  implicit val userEncoder: Encoder[User] = deriveEncoder[User]
  implicit val userProfilePhotosEncoder: Encoder[UserProfilePhotos] = deriveEncoder[UserProfilePhotos]
  implicit val venueEncoder: Encoder[Venue] = deriveEncoder[Venue]
  implicit val videoEncoder: Encoder[Video] = deriveEncoder[Video]
  implicit val videoNoteEncoder: Encoder[VideoNote] = deriveEncoder[VideoNote]
  implicit val voiceEncoder: Encoder[Voice] = deriveEncoder[Voice]

  implicit val loginUrlEncoder: Encoder[LoginUrl] = deriveEncoder[LoginUrl]

  // Payments
  implicit val currencyEncoder: Encoder[Currency] = Encoder[String].contramap(c => c.asInstanceOf[TelegramCurrency].code)
  implicit val labeledPriceEncoder: Encoder[LabeledPrice] = deriveEncoder[LabeledPrice]
  implicit val invoiceEncoder: Encoder[Invoice] = deriveEncoder[Invoice]
  implicit val shippingAddressEncoder: Encoder[ShippingAddress] = deriveEncoder[ShippingAddress]
  implicit val shippingQueryEncoder: Encoder[ShippingQuery] = deriveEncoder[ShippingQuery]
  implicit val orderInfoEncoder: Encoder[OrderInfo] = deriveEncoder[OrderInfo]
  implicit val preCheckoutQueryEncoder: Encoder[PreCheckoutQuery] = deriveEncoder[PreCheckoutQuery]
  implicit val shippingOptionEncoder: Encoder[ShippingOption] = deriveEncoder[ShippingOption]
  implicit val successfulPaymentEncoder: Encoder[SuccessfulPayment] = deriveEncoder[SuccessfulPayment]
  implicit val countryCodeEncoder: Encoder[CountryCode] = Encoder[String].contramap(c => CaseConversions.snakenize(c.toString))

  implicit val updateEncoder: Encoder[Update] = deriveEncoder[Update]

  // Inline
  implicit val inlineQueryEncoder: Encoder[InlineQuery] = deriveEncoder[InlineQuery]
  implicit val chosenInlineQueryEncoder: Encoder[ChosenInlineResult] = deriveEncoder[ChosenInlineResult]

  implicit val inputContactMessageContentEncoder: Encoder[InputContactMessageContent] =
    deriveEncoder[InputContactMessageContent]
  implicit val inputVenueMessageContent: Encoder[InputVenueMessageContent] =
    deriveEncoder[InputVenueMessageContent]
  implicit val inputLocationMessageContentEncoder: Encoder[InputLocationMessageContent] =
    deriveEncoder[InputLocationMessageContent]
  implicit val inputTextMessageContentEncoder: Encoder[InputTextMessageContent] =
    deriveEncoder[InputTextMessageContent]

  implicit val inputMessageContentEncoder: Encoder[InputMessageContent] =  Encoder.instance {
    case q: InputTextMessageContent => q.asJson
    case q: InputLocationMessageContent => q.asJson
    case q: InputVenueMessageContent => q.asJson
    case q: InputContactMessageContent => q.asJson
  }

  /** InlineQueryResult */
  implicit val inlineQueryResultArticleEncoder: Encoder[InlineQueryResultArticle] =
    deriveEncoder[InlineQueryResultArticle]
  implicit val inlineQueryResultPhotoEncoder: Encoder[InlineQueryResultPhoto] =
    deriveEncoder[InlineQueryResultPhoto]
  implicit val inlineQueryResultGifEncoder: Encoder[InlineQueryResultGif] =
    deriveEncoder[InlineQueryResultGif]
  implicit val inlineQueryResultMpeg4GifEncoder: Encoder[InlineQueryResultMpeg4Gif] =
    deriveEncoder[InlineQueryResultMpeg4Gif]

  implicit val inlineQueryResultVideoEncoder: Encoder[InlineQueryResultVideo] =
    deriveEncoder[InlineQueryResultVideo]

  implicit val inlineQueryResultAudioEncoder: Encoder[InlineQueryResultAudio] =
    deriveEncoder[InlineQueryResultAudio]

  implicit val inlineQueryResultVoiceEncoder: Encoder[InlineQueryResultVoice] =
    deriveEncoder[InlineQueryResultVoice]

  implicit val inlineQueryResultDocumentEncoder: Encoder[InlineQueryResultDocument] =
    deriveEncoder[InlineQueryResultDocument]

  implicit val inlineQueryResultLocationEncoder: Encoder[InlineQueryResultLocation] =
    deriveEncoder[InlineQueryResultLocation]

  implicit val inlineQueryResultVenueEncoder: Encoder[InlineQueryResultVenue] =
    deriveEncoder[InlineQueryResultVenue]

  implicit val inlineQueryResultContactEncoder: Encoder[InlineQueryResultContact] =
    deriveEncoder[InlineQueryResultContact]

  implicit val inlineQueryResultCachedPhotoEncoder: Encoder[InlineQueryResultCachedPhoto] =
    deriveEncoder[InlineQueryResultCachedPhoto]

  implicit val inlineQueryResultCachedGifEncoder: Encoder[InlineQueryResultCachedGif] =
    deriveEncoder[InlineQueryResultCachedGif]

  implicit val inlineQueryResultCachedMpeg4GifEncoder: Encoder[InlineQueryResultCachedMpeg4Gif] =
    deriveEncoder[InlineQueryResultCachedMpeg4Gif]

  implicit val inlineQueryResultCachedStickerEncoder: Encoder[InlineQueryResultCachedSticker] =
    deriveEncoder[InlineQueryResultCachedSticker]

  implicit val inlineQueryResultCachedDocumentEncoder: Encoder[InlineQueryResultCachedDocument] =
    deriveEncoder[InlineQueryResultCachedDocument]

  implicit val inlineQueryResultCachedVideoEncoder: Encoder[InlineQueryResultCachedVideo] =
    deriveEncoder[InlineQueryResultCachedVideo]

  implicit val inlineQueryResultCachedVoiceEncoder: Encoder[InlineQueryResultCachedVoice] =
    deriveEncoder[InlineQueryResultCachedVoice]

  implicit val inlineQueryResultCachedAudioEncoder: Encoder[InlineQueryResultCachedAudio] =
    deriveEncoder[InlineQueryResultCachedAudio]

  implicit val inlineQueryResultGameEncoder: Encoder[InlineQueryResultGame] = deriveEncoder[InlineQueryResultGame]

  implicit val inlineQueryResultEncoder: Encoder[InlineQueryResult] =  Encoder.instance {
    case q: InlineQueryResultCachedAudio => q.asJson
    case q: InlineQueryResultCachedDocument => q.asJson
    case q: InlineQueryResultCachedGif => q.asJson
    case q: InlineQueryResultCachedMpeg4Gif => q.asJson
    case q: InlineQueryResultCachedPhoto => q.asJson
    case q: InlineQueryResultCachedSticker => q.asJson
    case q: InlineQueryResultCachedVideo => q.asJson
    case q: InlineQueryResultCachedVoice => q.asJson
    case q: InlineQueryResultArticle => q.asJson
    case q: InlineQueryResultAudio => q.asJson
    case q: InlineQueryResultContact => q.asJson
    case q: InlineQueryResultDocument => q.asJson
    case q: InlineQueryResultGif => q.asJson
    case q: InlineQueryResultLocation => q.asJson
    case q: InlineQueryResultMpeg4Gif => q.asJson
    case q: InlineQueryResultPhoto => q.asJson
    case q: InlineQueryResultVenue => q.asJson
    case q: InlineQueryResultVideo => q.asJson
    case q: InlineQueryResultVoice => q.asJson
    case q: InlineQueryResultGame => q.asJson
  }

  implicit val answerInlineQueryEncoder: Encoder[AnswerInlineQuery] = deriveEncoder[AnswerInlineQuery]

  // Methods
  implicit val getMeEncoder: Encoder[GetMe.type] = Encoder.instance(_ ⇒ io.circe.Json.Null)
  implicit val deleteWebhookEncoder: Encoder[DeleteWebhook.type] = Encoder.instance(_ ⇒ io.circe.Json.Null)
  implicit val getWebhookInfoEncoder: Encoder[GetWebhookInfo.type] = Encoder.instance(_ ⇒ io.circe.Json.Null)

  implicit val updatesTypeEncoder: Encoder[UpdateType] = Encoder[String].contramap(e => CaseConversions.snakenize(e.toString))

  implicit val sendMessageEncoder: Encoder[SendMessage] = deriveEncoder[SendMessage]
  implicit val forwardMessageEncoder: Encoder[ForwardMessage] = deriveEncoder[ForwardMessage]
  implicit val getUpdatesEncoder: Encoder[GetUpdates] = deriveEncoder[GetUpdates]

  // Ignore InputFiles as JSON.
  implicit def inputFileEncoder: Encoder[InputFile] = Encoder.instance(_ ⇒ io.circe.Json.Null)

  implicit val sendLocationEncoder: Encoder[SendLocation] = deriveEncoder[SendLocation]
  implicit val sendVenueEncoder: Encoder[SendVenue] = deriveEncoder[SendVenue]
  implicit val sendContactEncoder: Encoder[SendContact] = deriveEncoder[SendContact]
  implicit val sendGameEncoder: Encoder[SendGame] = deriveEncoder[SendGame]
  implicit val setGameScoreEncoder: Encoder[SetGameScore] = deriveEncoder[SetGameScore]
  implicit val getGameHighScoresEncoder: Encoder[GetGameHighScores] = deriveEncoder[GetGameHighScores]

  // Payment Methods
  implicit val answerPreCheckoutQueryEncoder: Encoder[AnswerPreCheckoutQuery] = deriveEncoder[AnswerPreCheckoutQuery]
  implicit val answerShippingQueryEncoder: Encoder[AnswerShippingQuery] = deriveEncoder[AnswerShippingQuery]
  implicit val sendInvoiceEncoder: Encoder[SendInvoice] = deriveEncoder[SendInvoice]

  implicit val chatIdEncoder: Encoder[ChatId] = Encoder.instance {
    case ChatId.Chat(chat) => chat.asJson
    case ChatId.Channel(channel) => channel.asJson
  }

  implicit val chatActionEncoder: Encoder[ChatAction] =
    Encoder[String].contramap[ChatAction](e ⇒ CaseConversions.snakenize(e.toString))

  implicit val maskPositionTypeEncoder: Encoder[MaskPositionType] =
    Encoder[String].contramap[MaskPositionType](e ⇒ CaseConversions.snakenize(e.toString))

  implicit val sendChatActionEncoder: Encoder[SendChatAction] = deriveEncoder[SendChatAction]
  implicit val getUserProfilePhotosEncoder: Encoder[GetUserProfilePhotos] = deriveEncoder[GetUserProfilePhotos]
  implicit val getFileEncoder: Encoder[GetFile] = deriveEncoder[GetFile]
  implicit val kickChatMemberEncoder: Encoder[KickChatMember] = deriveEncoder[KickChatMember]
  implicit val leaveChatEncoder: Encoder[LeaveChat] = deriveEncoder[LeaveChat]
  implicit val unbanChatMemberEncoder: Encoder[UnbanChatMember] = deriveEncoder[UnbanChatMember]
  implicit val deleteMessageEncoder: Encoder[DeleteMessage] = deriveEncoder[DeleteMessage]
  implicit val getChatEncoder: Encoder[GetChat] = deriveEncoder[GetChat]
  implicit val getChatAdministratorsEncoder: Encoder[GetChatAdministrators] = deriveEncoder[GetChatAdministrators]
  implicit val getChatMembersCountEncoder: Encoder[GetChatMembersCount] = deriveEncoder[GetChatMembersCount]
  implicit val getChatMemberEncoder: Encoder[GetChatMember] = deriveEncoder[GetChatMember]
  implicit val answerCallbackQueryEncoder: Encoder[AnswerCallbackQuery] = deriveEncoder[AnswerCallbackQuery]

  implicit val editMessageTextEncoder: Encoder[EditMessageText] = deriveEncoder[EditMessageText]
  implicit val editMessageCaptionEncoder: Encoder[EditMessageCaption] = deriveEncoder[EditMessageCaption]
  implicit val editMessageReplyMarkupEncoder: Encoder[EditMessageReplyMarkup] = deriveEncoder[EditMessageReplyMarkup]

  implicit val deleteChatPhotoEncoder: Encoder[DeleteChatPhoto] = deriveEncoder[DeleteChatPhoto]

  implicit val deleteStickerFromSetEncoder: Encoder[DeleteStickerFromSet] = deriveEncoder[DeleteStickerFromSet]

  implicit val deleteChatStickerSetEncoder: Encoder[DeleteChatStickerSet] = deriveEncoder[DeleteChatStickerSet]

  implicit val editMessageLiveLocationEncoder: Encoder[EditMessageLiveLocation] = deriveEncoder[EditMessageLiveLocation]

  implicit val getStickerSetEncoder: Encoder[GetStickerSet] = deriveEncoder[GetStickerSet]

  implicit val pinChatMessageEncoder: Encoder[PinChatMessage] = deriveEncoder[PinChatMessage]

  implicit val promoteChatMemberEncoder: Encoder[PromoteChatMember] = deriveEncoder[PromoteChatMember]

  implicit val exportChatInviteLinkEncoder: Encoder[ExportChatInviteLink] = deriveEncoder[ExportChatInviteLink]

  implicit val restrictChatMemberEncoder: Encoder[RestrictChatMember] = deriveEncoder[RestrictChatMember]

  implicit val setChatDescriptionEncoder: Encoder[SetChatDescription] = deriveEncoder[SetChatDescription]

  implicit val setChatPermissionsEncoder: Encoder[SetChatPermissions] = deriveEncoder[SetChatPermissions]

  implicit val setChatStickerSetEncoder: Encoder[SetChatStickerSet] = deriveEncoder[SetChatStickerSet]

  implicit val setChatTitleEncoder: Encoder[SetChatTitle] = deriveEncoder[SetChatTitle]

  implicit val setStickerPositionInSetEncoder: Encoder[SetStickerPositionInSet] = deriveEncoder[SetStickerPositionInSet]

  implicit val stopMessageLiveLocationEncoder: Encoder[StopMessageLiveLocation] = deriveEncoder[StopMessageLiveLocation]

  implicit val unpinChatMessageEncoder: Encoder[UnpinChatMessage] = deriveEncoder[UnpinChatMessage]

  implicit val sendPollEncoder: Encoder[SendPoll] = deriveEncoder[SendPoll]
  implicit val StopPollEncoder: Encoder[StopPoll] = deriveEncoder[StopPoll]

  // Multipart methods
  implicit val addStickerToSetEncoder : Encoder[AddStickerToSet] = deriveEncoder[AddStickerToSet]
  implicit val createNewStickerSetEncoder : Encoder[CreateNewStickerSet] = deriveEncoder[CreateNewStickerSet]
  implicit val sendAudioEncoder : Encoder[SendAudio] = deriveEncoder[SendAudio]
  implicit val sendDocumentEncoder : Encoder[SendDocument] = deriveEncoder[SendDocument]
  implicit val sendMediaGroupEncoder : Encoder[SendMediaGroup] = deriveEncoder[SendMediaGroup]
  implicit val sendPhotoEncoder : Encoder[SendPhoto] = deriveEncoder[SendPhoto]
  implicit val sendStickerEncoder : Encoder[SendSticker] = deriveEncoder[SendSticker]
  implicit val sendVideoEncoder : Encoder[SendVideo] = deriveEncoder[SendVideo]
  implicit val sendVideoNoteEncoder : Encoder[SendVideoNote] = deriveEncoder[SendVideoNote]
  implicit val sendVoiceEncoder : Encoder[SendVoice] = deriveEncoder[SendVoice]
  implicit val setChatPhotoEncoder : Encoder[SetChatPhoto] = deriveEncoder[SetChatPhoto]
  implicit val setWebhookEncoder : Encoder[SetWebhook] = deriveEncoder[SetWebhook]
  implicit val uploadStickerFileEncoder : Encoder[UploadStickerFile] = deriveEncoder[UploadStickerFile]

}

object CirceEncoders extends CirceEncoders
