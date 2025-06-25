package com.bot4s.telegram.marshalling

import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.methods.PollType.PollType
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.CountryCode.CountryCode
import com.bot4s.telegram.models.Currency.{ Currency, TelegramCurrency }
import com.bot4s.telegram.models.StickerFormat.StickerFormat
import com.bot4s.telegram.models._
import io.circe.Encoder
import io.circe.generic.extras._
import io.circe.generic.extras.auto._
import io.circe.generic.extras.semiauto._
import io.circe.syntax._
import io.circe.JsonObject

/**
 * Circe marshalling borrowed/inspired from [[https://github.com/nikdon/telepooz]]
 */
trait CirceEncoders {

  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames

  // Models
  implicit val audioEncoder: Encoder[Audio]                 = deriveConfiguredEncoder[Audio]
  implicit val callbackQueryEncoder: Encoder[CallbackQuery] = deriveConfiguredEncoder[CallbackQuery]

  implicit val callbackGameEncoder: Encoder[CallbackGame] =
    Encoder.encodeJsonObject.contramap[CallbackGame](_ => JsonObject.empty)

  implicit val chatTypeEncoder: Encoder[ChatType] =
    Encoder[String].contramap[ChatType](e => CaseConversions.snakenize(e.toString))

  implicit val chatEncoder: Encoder[Chat] = deriveConfiguredEncoder[Chat]

  implicit val chatMemberEncoder: Encoder[ChatMember] = deriveConfiguredEncoder[ChatMember]

  implicit val contactEncoder: Encoder[Contact]   = deriveConfiguredEncoder[Contact]
  implicit val documentEncoder: Encoder[Document] = deriveConfiguredEncoder[Document]
  implicit val fileEncoder: Encoder[File]         = deriveConfiguredEncoder[File]

  implicit val inlineKeyboardButtonEncoder: Encoder[InlineKeyboardButton] =
    deriveConfiguredEncoder[InlineKeyboardButton]
  implicit val keyboardButtonEncoder: Encoder[KeyboardButton] = deriveConfiguredEncoder[KeyboardButton]
  implicit val locationEncoder: Encoder[Location]             = deriveConfiguredEncoder[Location]

  implicit val gameHighScoreEncoder: Encoder[GameHighScore] = deriveConfiguredEncoder[GameHighScore]
  implicit val animationEncoder: Encoder[Animation]         = deriveConfiguredEncoder[Animation]
  implicit val gameEncoder: Encoder[Game]                   = deriveConfiguredEncoder[Game]

  implicit val writeAccessAllowedEncoder: Encoder[WriteAccessAllowed] =
    deriveConfiguredEncoder[WriteAccessAllowed]
  implicit val maybeWriteAccessAllowedEncoder: Encoder[Option[WriteAccessAllowed]] =
    Encoder.encodeOption[WriteAccessAllowed]
  implicit val messageEncoder: Encoder[Message]     = deriveConfiguredEncoder[Message]
  implicit val messageIdEncoder: Encoder[MessageId] = deriveConfiguredEncoder[MessageId]

  implicit val messageEntityTypeEncoder: Encoder[MessageEntityType] =
    Encoder[String].contramap[MessageEntityType](e => CaseConversions.snakenize(e.toString))

  implicit val stickerTypeEncoder: Encoder[StickerType] =
    Encoder[String].contramap[StickerType](e => CaseConversions.snakenize(e.toString))

  implicit val messageEntityEncoder: Encoder[MessageEntity] = deriveConfiguredEncoder[MessageEntity]

  implicit val parseModeEncoder: Encoder[ParseMode] =
    Encoder[String].contramap[ParseMode](e => CaseConversions.snakenize(e.toString))

  implicit val pollTypeEncoder: Encoder[PollType] =
    Encoder[String].contramap[PollType](e => CaseConversions.snakenize(e.toString))

  implicit val photoSizeEncoder: Encoder[PhotoSize] = deriveConfiguredEncoder[PhotoSize]

  implicit val botCommandScopeEncoder: Encoder[BotCommandScope] =
    Encoder[String].contramap(e => CaseConversions.snakenize(e.toString))

  implicit val memberStatusEncoder: Encoder[MemberStatus] =
    Encoder[String].contramap(e => CaseConversions.snakenize(e.toString))

  implicit val replyMarkupEncoder: Encoder[ReplyMarkup] = Encoder.instance {
    case fr: ForceReply            => fr.asJson
    case ikm: InlineKeyboardMarkup => ikm.asJson
    case rkr: ReplyKeyboardRemove  => rkr.asJson
    case rkm: ReplyKeyboardMarkup  => rkm.asJson
  }

  implicit val stickerEncoder: Encoder[Sticker]           = deriveConfiguredEncoder[Sticker]
  implicit val maskPositionEncoder: Encoder[MaskPosition] = deriveConfiguredEncoder[MaskPosition]

  implicit val userEncoder: Encoder[User]                           = deriveConfiguredEncoder[User]
  implicit val userProfilePhotosEncoder: Encoder[UserProfilePhotos] = deriveConfiguredEncoder[UserProfilePhotos]
  implicit val venueEncoder: Encoder[Venue]                         = deriveConfiguredEncoder[Venue]
  implicit val videoEncoder: Encoder[Video]                         = deriveConfiguredEncoder[Video]
  implicit val videoNoteEncoder: Encoder[VideoNote]                 = deriveConfiguredEncoder[VideoNote]
  implicit val voiceEncoder: Encoder[Voice]                         = deriveConfiguredEncoder[Voice]

  implicit val storyEncoder: Encoder[Story.type] = deriveConfiguredEncoder[Story.type]

  implicit val loginUrlEncoder: Encoder[LoginUrl] = deriveConfiguredEncoder[LoginUrl]

  // Payments
  implicit val currencyEncoder: Encoder[Currency] =
    Encoder[String].contramap(c => c.asInstanceOf[TelegramCurrency].code)
  implicit val labeledPriceEncoder: Encoder[LabeledPrice]           = deriveConfiguredEncoder[LabeledPrice]
  implicit val invoiceEncoder: Encoder[Invoice]                     = deriveConfiguredEncoder[Invoice]
  implicit val shippingAddressEncoder: Encoder[ShippingAddress]     = deriveConfiguredEncoder[ShippingAddress]
  implicit val shippingQueryEncoder: Encoder[ShippingQuery]         = deriveConfiguredEncoder[ShippingQuery]
  implicit val orderInfoEncoder: Encoder[OrderInfo]                 = deriveConfiguredEncoder[OrderInfo]
  implicit val preCheckoutQueryEncoder: Encoder[PreCheckoutQuery]   = deriveConfiguredEncoder[PreCheckoutQuery]
  implicit val shippingOptionEncoder: Encoder[ShippingOption]       = deriveConfiguredEncoder[ShippingOption]
  implicit val successfulPaymentEncoder: Encoder[SuccessfulPayment] = deriveConfiguredEncoder[SuccessfulPayment]
  implicit val countryCodeEncoder: Encoder[CountryCode] =
    Encoder[String].contramap(c => CaseConversions.snakenize(c.toString))

  implicit val updateEncoder: Encoder[Update] = deriveConfiguredEncoder[Update]

  // Inline
  implicit val inlineQueryEncoder: Encoder[InlineQuery]              = deriveConfiguredEncoder[InlineQuery]
  implicit val chosenInlineQueryEncoder: Encoder[ChosenInlineResult] = deriveConfiguredEncoder[ChosenInlineResult]

  implicit val inputContactMessageContentEncoder: Encoder[InputContactMessageContent] =
    deriveConfiguredEncoder[InputContactMessageContent]
  implicit val inputVenueMessageContent: Encoder[InputVenueMessageContent] =
    deriveConfiguredEncoder[InputVenueMessageContent]
  implicit val inputLocationMessageContentEncoder: Encoder[InputLocationMessageContent] =
    deriveConfiguredEncoder[InputLocationMessageContent]
  implicit val inputTextMessageContentEncoder: Encoder[InputTextMessageContent] =
    deriveConfiguredEncoder[InputTextMessageContent]

  implicit val inputMessageContentEncoder: Encoder[InputMessageContent] = Encoder.instance {
    case q: InputTextMessageContent     => q.asJson
    case q: InputLocationMessageContent => q.asJson
    case q: InputVenueMessageContent    => q.asJson
    case q: InputContactMessageContent  => q.asJson
  }

  /** InlineQueryResult */
  implicit val inlineQueryResultArticleEncoder: Encoder[InlineQueryResultArticle] =
    deriveConfiguredEncoder[InlineQueryResultArticle]
  implicit val inlineQueryResultPhotoEncoder: Encoder[InlineQueryResultPhoto] =
    deriveConfiguredEncoder[InlineQueryResultPhoto]
  implicit val inlineQueryResultGifEncoder: Encoder[InlineQueryResultGif] =
    deriveConfiguredEncoder[InlineQueryResultGif]
  implicit val inlineQueryResultMpeg4GifEncoder: Encoder[InlineQueryResultMpeg4Gif] =
    deriveConfiguredEncoder[InlineQueryResultMpeg4Gif]

  implicit val inlineQueryResultVideoEncoder: Encoder[InlineQueryResultVideo] =
    deriveConfiguredEncoder[InlineQueryResultVideo]

  implicit val inlineQueryResultAudioEncoder: Encoder[InlineQueryResultAudio] =
    deriveConfiguredEncoder[InlineQueryResultAudio]

  implicit val inlineQueryResultVoiceEncoder: Encoder[InlineQueryResultVoice] =
    deriveConfiguredEncoder[InlineQueryResultVoice]

  implicit val inlineQueryResultDocumentEncoder: Encoder[InlineQueryResultDocument] =
    deriveConfiguredEncoder[InlineQueryResultDocument]

  implicit val inlineQueryResultLocationEncoder: Encoder[InlineQueryResultLocation] =
    deriveConfiguredEncoder[InlineQueryResultLocation]

  implicit val inlineQueryResultVenueEncoder: Encoder[InlineQueryResultVenue] =
    deriveConfiguredEncoder[InlineQueryResultVenue]

  implicit val inlineQueryResultContactEncoder: Encoder[InlineQueryResultContact] =
    deriveConfiguredEncoder[InlineQueryResultContact]

  implicit val inlineQueryResultCachedPhotoEncoder: Encoder[InlineQueryResultCachedPhoto] =
    deriveConfiguredEncoder[InlineQueryResultCachedPhoto]

  implicit val inlineQueryResultCachedGifEncoder: Encoder[InlineQueryResultCachedGif] =
    deriveConfiguredEncoder[InlineQueryResultCachedGif]

  implicit val inlineQueryResultCachedMpeg4GifEncoder: Encoder[InlineQueryResultCachedMpeg4Gif] =
    deriveConfiguredEncoder[InlineQueryResultCachedMpeg4Gif]

  implicit val inlineQueryResultCachedStickerEncoder: Encoder[InlineQueryResultCachedSticker] =
    deriveConfiguredEncoder[InlineQueryResultCachedSticker]

  implicit val inlineQueryResultCachedDocumentEncoder: Encoder[InlineQueryResultCachedDocument] =
    deriveConfiguredEncoder[InlineQueryResultCachedDocument]

  implicit val inlineQueryResultCachedVideoEncoder: Encoder[InlineQueryResultCachedVideo] =
    deriveConfiguredEncoder[InlineQueryResultCachedVideo]

  implicit val inlineQueryResultCachedVoiceEncoder: Encoder[InlineQueryResultCachedVoice] =
    deriveConfiguredEncoder[InlineQueryResultCachedVoice]

  implicit val inlineQueryResultCachedAudioEncoder: Encoder[InlineQueryResultCachedAudio] =
    deriveConfiguredEncoder[InlineQueryResultCachedAudio]

  implicit val inlineQueryResultGameEncoder: Encoder[InlineQueryResultGame] =
    deriveConfiguredEncoder[InlineQueryResultGame]

  implicit val inlineQueryResultEncoder: Encoder[InlineQueryResult] = Encoder.instance {
    case q: InlineQueryResultCachedAudio    => q.asJson
    case q: InlineQueryResultCachedDocument => q.asJson
    case q: InlineQueryResultCachedGif      => q.asJson
    case q: InlineQueryResultCachedMpeg4Gif => q.asJson
    case q: InlineQueryResultCachedPhoto    => q.asJson
    case q: InlineQueryResultCachedSticker  => q.asJson
    case q: InlineQueryResultCachedVideo    => q.asJson
    case q: InlineQueryResultCachedVoice    => q.asJson
    case q: InlineQueryResultArticle        => q.asJson
    case q: InlineQueryResultAudio          => q.asJson
    case q: InlineQueryResultContact        => q.asJson
    case q: InlineQueryResultDocument       => q.asJson
    case q: InlineQueryResultGif            => q.asJson
    case q: InlineQueryResultLocation       => q.asJson
    case q: InlineQueryResultMpeg4Gif       => q.asJson
    case q: InlineQueryResultPhoto          => q.asJson
    case q: InlineQueryResultVenue          => q.asJson
    case q: InlineQueryResultVideo          => q.asJson
    case q: InlineQueryResultVoice          => q.asJson
    case q: InlineQueryResultGame           => q.asJson
  }

  implicit val answerInlineQueryEncoder: Encoder[AnswerInlineQuery] = deriveConfiguredEncoder[AnswerInlineQuery]

  // MenuButton

  implicit val menuButtonDefaultEncoder: Encoder[MenuButtonDefault] =
    deriveConfiguredEncoder[MenuButtonDefault]

  implicit val menuButtonWebAppEncoder: Encoder[MenuButtonWebApp] =
    deriveConfiguredEncoder[MenuButtonWebApp]

  implicit val menuButtonCommandsEncoder: Encoder[MenuButtonCommands] =
    deriveConfiguredEncoder[MenuButtonCommands]

  implicit val menuButtonEncoder: Encoder[MenuButton] = Encoder.instance {
    case q: MenuButtonDefault  => q.asJson
    case q: MenuButtonWebApp   => q.asJson
    case q: MenuButtonCommands => q.asJson
  }

  // Methods
  implicit val getMeEncoder: Encoder[GetMe.type]                   = Encoder.instance(_ => io.circe.Json.Null)
  implicit val deleteWebhookEncoder: Encoder[DeleteWebhook.type]   = Encoder.instance(_ => io.circe.Json.Null)
  implicit val getWebhookInfoEncoder: Encoder[GetWebhookInfo.type] = Encoder.instance(_ => io.circe.Json.Null)

  implicit val updatesTypeEncoder: Encoder[UpdateType] =
    Encoder[String].contramap(e => CaseConversions.snakenize(e.toString))

  implicit val sendMessageEncoder: Encoder[SendMessage]       = deriveConfiguredEncoder[SendMessage]
  implicit val forwardMessageEncoder: Encoder[ForwardMessage] = deriveConfiguredEncoder[ForwardMessage]
  implicit val copyMessageEncoder: Encoder[CopyMessage]       = deriveConfiguredEncoder[CopyMessage]
  implicit val sendDiceEncoder: Encoder[SendDice]             = deriveConfiguredEncoder[SendDice]
  implicit val getUpdatesEncoder: Encoder[GetUpdates]         = deriveConfiguredEncoder[GetUpdates]

  implicit val chatLocationEncoder: Encoder[ChatLocation] = deriveConfiguredEncoder[ChatLocation]
  // for v6.8 support
  implicit val unpinAllGeneralForumTopicMessagesEncoder: Encoder[UnpinAllGeneralForumTopicMessages] =
    deriveConfiguredEncoder[UnpinAllGeneralForumTopicMessages]
  // for v6.7 support
  implicit val getMyNameEncoder: Encoder[GetMyName] = deriveConfiguredEncoder[GetMyName]
  implicit val setMyNameEncoder: Encoder[SetMyName] = deriveConfiguredEncoder[SetMyName]
  // for v6.6 support
  implicit val stickerFormatEncoder: Encoder[StickerFormat] =
    Encoder[String].contramap[StickerFormat](e => CaseConversions.snakenize(e.toString))
  implicit val createNewStickerSetEncoder: Encoder[CreateNewStickerSet] = deriveConfiguredEncoder[CreateNewStickerSet]
  implicit val setCustomEmojiStickerSetThumbnailEncoder: Encoder[SetCustomEmojiStickerSetThumbnail] =
    deriveConfiguredEncoder[SetCustomEmojiStickerSetThumbnail]
  implicit val setStickerSetTitleEncoder: Encoder[SetStickerSetTitle] =
    deriveConfiguredEncoder[SetStickerSetTitle]
  implicit val setStickerSetThumbnailEncoder: Encoder[SetStickerSetThumbnail] =
    deriveConfiguredEncoder[SetStickerSetThumbnail]
  implicit val deleteStickerSetEncoder: Encoder[DeleteStickerSet] =
    deriveConfiguredEncoder[DeleteStickerSet]
  implicit val setStickerEmojiListEncoder: Encoder[SetStickerEmojiList] =
    deriveConfiguredEncoder[SetStickerEmojiList]
  implicit val setStickerKeywordsEncoder: Encoder[SetStickerKeywords] =
    deriveConfiguredEncoder[SetStickerKeywords]
  implicit val setStickerMaskPositionEncoder: Encoder[SetStickerMaskPosition] =
    deriveConfiguredEncoder[SetStickerMaskPosition]
  implicit val getMyShortDescriptionEncoder: Encoder[GetMyShortDescription] =
    deriveConfiguredEncoder[GetMyShortDescription]
  implicit val setMyShortDescriptionEncoder: Encoder[SetMyShortDescription] =
    deriveConfiguredEncoder[SetMyShortDescription]
  implicit val botShortDescriptionEncoder: Encoder[BotShortDescription] =
    deriveConfiguredEncoder[BotShortDescription]
  implicit val botDescriptionEncoder: Encoder[BotDescription] =
    deriveConfiguredEncoder[BotDescription]
  implicit val getMyDescriptionEncoder: Encoder[GetMyDescription] =
    deriveConfiguredEncoder[GetMyDescription]
  implicit val setMyDescriptionEncoder: Encoder[SetMyDescription] =
    deriveConfiguredEncoder[SetMyDescription]

  // for v6.4 support
  implicit val editGeneralForumTopicEncoder: Encoder[EditGeneralForumTopic] =
    deriveConfiguredEncoder[EditGeneralForumTopic]
  implicit val closeGeneralForumTopicEncoder: Encoder[CloseGeneralForumTopic] =
    deriveConfiguredEncoder[CloseGeneralForumTopic]
  implicit val reopenGeneralForumTopicEncoder: Encoder[ReopenGeneralForumTopic] =
    deriveConfiguredEncoder[ReopenGeneralForumTopic]
  implicit val hideGeneralForumTopicEncoder: Encoder[HideGeneralForumTopic] =
    deriveConfiguredEncoder[HideGeneralForumTopic]
  implicit val unhideGeneralForumTopicEncoder: Encoder[UnhideGeneralForumTopic] =
    deriveConfiguredEncoder[UnhideGeneralForumTopic]
  implicit val generalForumTopicHiddenEncoder: Encoder[GeneralForumTopicHidden.type] =
    deriveConfiguredEncoder[GeneralForumTopicHidden.type]
  implicit val generalForumTopicUnhiddenEncoder: Encoder[GeneralForumTopicUnhidden.type] =
    deriveConfiguredEncoder[GeneralForumTopicUnhidden.type]
  // for v6.3 support
  implicit val forumTopicEncoder: Encoder[ForumTopic]             = deriveConfiguredEncoder[ForumTopic]
  implicit val createForumTopicEncoder: Encoder[CreateForumTopic] = deriveConfiguredEncoder[CreateForumTopic]
  implicit val editForumTopicEncoder: Encoder[EditForumTopic]     = deriveConfiguredEncoder[EditForumTopic]
  implicit val closeForumTopicEncoder: Encoder[CloseForumTopic]   = deriveConfiguredEncoder[CloseForumTopic]
  implicit val reopenForumTopicEncoder: Encoder[ReopenForumTopic] = deriveConfiguredEncoder[ReopenForumTopic]
  implicit val deleteForumTopicEncoder: Encoder[DeleteForumTopic] = deriveConfiguredEncoder[DeleteForumTopic]
  implicit val unpinAllForumTopicMessagesEncoder: Encoder[UnpinAllForumTopicMessages] =
    deriveConfiguredEncoder[UnpinAllForumTopicMessages]
  implicit val getForumTopicIconStickersEncoder: Encoder[GetForumTopicIconStickers.type] =
    deriveConfiguredEncoder[GetForumTopicIconStickers.type]
  // for v6.2 support
  implicit val getCustomEmojiStickers: Encoder[GetCustomEmojiStickers] = deriveConfiguredEncoder[GetCustomEmojiStickers]
  // for v6.1 support
  implicit val createInvoiceLinkEncoder: Encoder[CreateInvoiceLink] = deriveConfiguredEncoder[CreateInvoiceLink]
  // for v6.0 support
  implicit val webAppInfoEncoder: Encoder[WebAppInfo] = deriveConfiguredEncoder[WebAppInfo]
  implicit val webAppDataEncoder: Encoder[WebAppData] = deriveConfiguredEncoder[WebAppData]
  implicit val chatAdminRightsEncoder: Encoder[ChatAdministratorRights] =
    deriveConfiguredEncoder[ChatAdministratorRights]
  implicit val answerWebAppQueryEncoder: Encoder[AnswerWebAppQuery] = deriveConfiguredEncoder[AnswerWebAppQuery]
  implicit val sentWebAppMessageEncoder: Encoder[SentWebAppMessage] = deriveConfiguredEncoder[SentWebAppMessage]

  // for v5.1 support
  implicit val chatInviteLinkEncoder: Encoder[ChatInviteLink]       = deriveConfiguredEncoder[ChatInviteLink]
  implicit val chatMemberUpdatedEncoder: Encoder[ChatMemberUpdated] = deriveConfiguredEncoder[ChatMemberUpdated]

  implicit val createChatInviteLinkEncoder: Encoder[CreateChatInviteLink] =
    deriveConfiguredEncoder[CreateChatInviteLink]
  implicit val editChatInviteLinkEncoder: Encoder[EditChatInviteLink] = deriveConfiguredEncoder[EditChatInviteLink]
  implicit val approveChatJoinrequestEncoder: Encoder[ApproveChatJoinRequest] =
    deriveConfiguredEncoder[ApproveChatJoinRequest]
  implicit val declineChatJoinrequestEncoder: Encoder[DeclineChatJoinRequest] =
    deriveConfiguredEncoder[DeclineChatJoinRequest]

  implicit val inputFileEncoder: Encoder[InputFile] = Encoder.instance {
    case InputFile.FileId(fileId) => fileId.asJson
    case _                        => io.circe.Json.Null
  }

  implicit val inputMediaEncoder: Encoder[InputMedia] = Encoder.instance {
    case q: InputMediaPhoto     => q.asJson
    case q: InputMediaVideo     => q.asJson
    case q: InputMediaAnimation => q.asJson
    case q: InputMediaAudio     => q.asJson
    case q: InputMediaDocument  => q.asJson
  }

  implicit val sendLocationEncoder: Encoder[SendLocation]           = deriveConfiguredEncoder[SendLocation]
  implicit val sendVenueEncoder: Encoder[SendVenue]                 = deriveConfiguredEncoder[SendVenue]
  implicit val sendContactEncoder: Encoder[SendContact]             = deriveConfiguredEncoder[SendContact]
  implicit val sendGameEncoder: Encoder[SendGame]                   = deriveConfiguredEncoder[SendGame]
  implicit val setGameScoreEncoder: Encoder[SetGameScore]           = deriveConfiguredEncoder[SetGameScore]
  implicit val getGameHighScoresEncoder: Encoder[GetGameHighScores] = deriveConfiguredEncoder[GetGameHighScores]

  // Payment Methods
  implicit val answerPreCheckoutQueryEncoder: Encoder[AnswerPreCheckoutQuery] =
    deriveConfiguredEncoder[AnswerPreCheckoutQuery]
  implicit val answerShippingQueryEncoder: Encoder[AnswerShippingQuery] = deriveConfiguredEncoder[AnswerShippingQuery]
  implicit val sendInvoiceEncoder: Encoder[SendInvoice]                 = deriveConfiguredEncoder[SendInvoice]

  implicit val chatIdEncoder: Encoder[ChatId] = Encoder.instance {
    case ChatId.Chat(chat)       => chat.asJson
    case ChatId.Channel(channel) => channel.asJson
  }

  implicit val chatActionEncoder: Encoder[ChatAction] =
    Encoder[String].contramap[ChatAction](e => CaseConversions.snakenize(e.toString))

  implicit val maskPositionTypeEncoder: Encoder[MaskPositionType] =
    Encoder[String].contramap[MaskPositionType](e => CaseConversions.snakenize(e.toString))

  implicit val setMyCommandsEncoder: Encoder[SetMyCommands] = deriveConfiguredEncoder[SetMyCommands]
  implicit val getMyCommandsEncoder: Encoder[GetMyCommands] = deriveConfiguredEncoder[GetMyCommands]
  implicit val deleteMyCommands: Encoder[DeleteMyCommands]  = deriveConfiguredEncoder[DeleteMyCommands]

  implicit val sendChatActionEncoder: Encoder[SendChatAction] = deriveConfiguredEncoder[SendChatAction]
  implicit val getUserProfilePhotosEncoder: Encoder[GetUserProfilePhotos] =
    deriveConfiguredEncoder[GetUserProfilePhotos]
  implicit val getFileEncoder: Encoder[GetFile]                         = deriveConfiguredEncoder[GetFile]
  implicit val banChatMemberEncoder: Encoder[BanChatMember]             = deriveConfiguredEncoder[BanChatMember]
  implicit val banChatSenderChatEncoder: Encoder[BanChatSenderChat]     = deriveConfiguredEncoder[BanChatSenderChat]
  implicit val kickChatMemberEncoder: Encoder[KickChatMember]           = deriveConfiguredEncoder[KickChatMember]
  implicit val leaveChatEncoder: Encoder[LeaveChat]                     = deriveConfiguredEncoder[LeaveChat]
  implicit val unbanChatMemberEncoder: Encoder[UnbanChatMember]         = deriveConfiguredEncoder[UnbanChatMember]
  implicit val unbanChatSenderChatEncoder: Encoder[UnbanChatSenderChat] = deriveConfiguredEncoder[UnbanChatSenderChat]
  implicit val deleteMessageEncoder: Encoder[DeleteMessage]             = deriveConfiguredEncoder[DeleteMessage]
  implicit val getChatEncoder: Encoder[GetChat]                         = deriveConfiguredEncoder[GetChat]
  implicit val getChatAdministratorsEncoder: Encoder[GetChatAdministrators] =
    deriveConfiguredEncoder[GetChatAdministrators]
  implicit val getChatMemberCountEncoder: Encoder[GetChatMemberCount]   = deriveConfiguredEncoder[GetChatMemberCount]
  implicit val getChatMembersCountEncoder: Encoder[GetChatMembersCount] = deriveConfiguredEncoder[GetChatMembersCount]
  implicit val getChatMemberEncoder: Encoder[GetChatMember]             = deriveConfiguredEncoder[GetChatMember]
  implicit val getChatMenuButtonEncoder: Encoder[GetChatMenuButton]     = deriveConfiguredEncoder[GetChatMenuButton]
  implicit val answerCallbackQueryEncoder: Encoder[AnswerCallbackQuery] = deriveConfiguredEncoder[AnswerCallbackQuery]

  implicit val editMessageTextEncoder: Encoder[EditMessageText]       = deriveConfiguredEncoder[EditMessageText]
  implicit val editMessageCaptionEncoder: Encoder[EditMessageCaption] = deriveConfiguredEncoder[EditMessageCaption]
  implicit val editMessageReplyMarkupEncoder: Encoder[EditMessageReplyMarkup] =
    deriveConfiguredEncoder[EditMessageReplyMarkup]

  implicit val deleteChatPhotoEncoder: Encoder[DeleteChatPhoto] = deriveConfiguredEncoder[DeleteChatPhoto]

  implicit val deleteStickerFromSetEncoder: Encoder[DeleteStickerFromSet] =
    deriveConfiguredEncoder[DeleteStickerFromSet]

  implicit val deleteChatStickerSetEncoder: Encoder[DeleteChatStickerSet] =
    deriveConfiguredEncoder[DeleteChatStickerSet]

  implicit val editMessageLiveLocationEncoder: Encoder[EditMessageLiveLocation] =
    deriveConfiguredEncoder[EditMessageLiveLocation]

  implicit val getStickerSetEncoder: Encoder[GetStickerSet] = deriveConfiguredEncoder[GetStickerSet]

  implicit val pinChatMessageEncoder: Encoder[PinChatMessage] = deriveConfiguredEncoder[PinChatMessage]

  implicit val promoteChatMemberEncoder: Encoder[PromoteChatMember] = deriveConfiguredEncoder[PromoteChatMember]

  implicit val exportChatInviteLinkEncoder: Encoder[ExportChatInviteLink] =
    deriveConfiguredEncoder[ExportChatInviteLink]

  implicit val restrictChatMemberEncoder: Encoder[RestrictChatMember] = deriveConfiguredEncoder[RestrictChatMember]

  implicit val setChatDescriptionEncoder: Encoder[SetChatDescription] = deriveConfiguredEncoder[SetChatDescription]

  implicit val setChatAdministratorCustomTitleEncoder: Encoder[SetChatAdministratorCustomTitle] =
    deriveConfiguredEncoder[SetChatAdministratorCustomTitle]

  implicit val setChatPermissionsEncoder: Encoder[SetChatPermissions] = deriveConfiguredEncoder[SetChatPermissions]

  implicit val setChatStickerSetEncoder: Encoder[SetChatStickerSet] = deriveConfiguredEncoder[SetChatStickerSet]

  implicit val setChatTitleEncoder: Encoder[SetChatTitle]       = deriveConfiguredEncoder[SetChatTitle]
  implicit val setChatButtonEncoder: Encoder[SetChatMenuButton] = deriveConfiguredEncoder[SetChatMenuButton]

  implicit val setStickerPositionInSetEncoder: Encoder[SetStickerPositionInSet] =
    deriveConfiguredEncoder[SetStickerPositionInSet]

  implicit val stopMessageLiveLocationEncoder: Encoder[StopMessageLiveLocation] =
    deriveConfiguredEncoder[StopMessageLiveLocation]

  implicit val unpinChatMessageEncoder: Encoder[UnpinChatMessage] = deriveConfiguredEncoder[UnpinChatMessage]

  implicit val sendPollEncoder: Encoder[SendPoll] = deriveConfiguredEncoder[SendPoll]
  implicit val StopPollEncoder: Encoder[StopPoll] = deriveConfiguredEncoder[StopPoll]

  // Multipart methods
  implicit val addStickerToSetEncoder: Encoder[AddStickerToSet]     = deriveConfiguredEncoder[AddStickerToSet]
  implicit val sendAnimationEncoder: Encoder[SendAnimation]         = deriveConfiguredEncoder[SendAnimation]
  implicit val sendAudioEncoder: Encoder[SendAudio]                 = deriveConfiguredEncoder[SendAudio]
  implicit val sendDocumentEncoder: Encoder[SendDocument]           = deriveConfiguredEncoder[SendDocument]
  implicit val editMessageMediaEncoder: Encoder[EditMessageMedia]   = deriveConfiguredEncoder[EditMessageMedia]
  implicit val sendMediaGroupEncoder: Encoder[SendMediaGroup]       = deriveConfiguredEncoder[SendMediaGroup]
  implicit val sendPhotoEncoder: Encoder[SendPhoto]                 = deriveConfiguredEncoder[SendPhoto]
  implicit val sendStickerEncoder: Encoder[SendSticker]             = deriveConfiguredEncoder[SendSticker]
  implicit val sendVideoEncoder: Encoder[SendVideo]                 = deriveConfiguredEncoder[SendVideo]
  implicit val sendVideoNoteEncoder: Encoder[SendVideoNote]         = deriveConfiguredEncoder[SendVideoNote]
  implicit val sendVoiceEncoder: Encoder[SendVoice]                 = deriveConfiguredEncoder[SendVoice]
  implicit val setChatPhotoEncoder: Encoder[SetChatPhoto]           = deriveConfiguredEncoder[SetChatPhoto]
  implicit val setWebhookEncoder: Encoder[SetWebhook]               = deriveConfiguredEncoder[SetWebhook]
  implicit val uploadStickerFileEncoder: Encoder[UploadStickerFile] = deriveConfiguredEncoder[UploadStickerFile]

}

object CirceEncoders extends CirceEncoders
