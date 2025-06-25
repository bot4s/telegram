package com.bot4s.telegram.marshalling

import com.bot4s.telegram.methods.*
import com.bot4s.telegram.methods.ParseMode.ParseMode
import com.bot4s.telegram.methods.PollType.PollType
import com.bot4s.telegram.models.*
import com.bot4s.telegram.models.CountryCode.CountryCode
import com.bot4s.telegram.models.Currency.Currency
import com.bot4s.telegram.models.StickerFormat.StickerFormat
import com.typesafe.scalalogging.StrictLogging
import io.circe.generic.extras.semiauto.deriveConfiguredDecoder
import io.circe.generic.semiauto.*
import io.circe.{ Decoder, HCursor, Json }

import java.util.NoSuchElementException

/**
 * Circe marshalling borrowed/inspired from [[https://github.com/nikdon/telepooz]]
 */
trait CirceDecoders extends StrictLogging {

  implicit val booleanDecoder: Decoder[Boolean] = Decoder.decodeBoolean
  implicit val stringDecoder: Decoder[String]   = Decoder.decodeString

  implicit def eitherDecoder[A, B](implicit decA: Decoder[A], decB: Decoder[B]): Decoder[Either[A, B]] = {
    val l: Decoder[Either[A, B]] = decA.map(Left.apply)
    val r: Decoder[Either[A, B]] = decB.map(Right.apply)
    l or r
  }

  implicit val botCommandScopeDecoder: Decoder[BotCommandScope] =
    Decoder[String].map(s => BotCommandScope.valueOf(pascalize(s)))
  implicit val memberStatusDecoder: Decoder[MemberStatus] =
    Decoder[String].map(s => MemberStatus.valueOf(pascalize(s)))
  implicit val maskPositionTypeDecoder: Decoder[MaskPositionType] =
    Decoder[String].map(s => MaskPositionType.valueOf(pascalize(s)))

  implicit val chatTypeDecoder: Decoder[ChatType] = Decoder[String].map(s => ChatType.valueOf(pascalize(s)))

  implicit val messageEntityTypeDecoder: Decoder[MessageEntityType] =
    Decoder[String].map { s =>
      try {
        MessageEntityType.valueOf(pascalize(s))
      } catch {
        case e: (NoSuchElementException | IllegalArgumentException) =>
          logger.warn(s"Unexpected MessageEntityType: '$s', fallback to Unknown.")
          MessageEntityType.Unknown
      }
    }

  implicit val stickerTypeDecoder: Decoder[StickerType] =
    Decoder[String].map { s =>
      try {
        StickerType.valueOf(pascalize(s))
      } catch {
        case e: (NoSuchElementException | IllegalArgumentException) =>
          logger.warn(s"Unexpected StickerType: '$s', fallback to Unknown.")
          StickerType.Unknown
      }
    }

  implicit val parseModeDecoder: Decoder[ParseMode] =
    Decoder[String].map(s => ParseMode.withName(pascalize(s)))

  implicit val pollTypeDecoder: Decoder[PollType] =
    Decoder[String].map(s => PollType.withName(pascalize(s)))

  implicit val countryCodeDecoder: Decoder[CountryCode] =
    Decoder[String].map(a => CountryCode.withName(a))

  implicit val currencyDecoder: Decoder[Currency] =
    Decoder[String].map(a => Currency.withName(a))

  implicit val chatIdDecoder: Decoder[ChatId] =
    Decoder[String].map(ChatId.Channel.apply) or Decoder[Long].map(ChatId.Chat.apply)

  implicit val chatActionDecoder: Decoder[ChatAction] =
    Decoder[String].map(s => ChatAction.valueOf(pascalize(s)))

  implicit val updateTypeDecoder: Decoder[UpdateType] = Decoder[String].map(s => UpdateType.valueOf(pascalize(s)))

  implicit val listBotCommandDecoder: Decoder[List[BotCommand]] = deriveDecoder[List[BotCommand]]
  implicit val botCommandDecoder: Decoder[BotCommand]           = deriveDecoder[BotCommand]

  implicit val chatLocationDecoder: Decoder[ChatLocation] = deriveDecoder[ChatLocation]
  // for v6.7 support
  implicit val botNameDecoder: Decoder[BotName] = deriveDecoder[BotName]
  implicit val inlineQueryResultsButtonDecoder: Decoder[InlineQueryResultsButton] =
    deriveDecoder[InlineQueryResultsButton]
  implicit val switchInlineQueryChosenChatDecoder: Decoder[SwitchInlineQueryChosenChat] =
    deriveDecoder[SwitchInlineQueryChosenChat]
  // for v6.6 support
  implicit val stickerFormatDecoder: Decoder[StickerFormat] =
    Decoder[String].map(s => StickerFormat.withName(pascalize(s)))

  implicit val botDescriptionDecoder: Decoder[BotDescription]           = deriveDecoder[BotDescription]
  implicit val botShortDescriptionDecoder: Decoder[BotShortDescription] = deriveDecoder[BotShortDescription]
  // for v6.5 support
  implicit val KeyboardButtonRequestUserDecoder: Decoder[KeyboardButtonRequestUser] =
    deriveDecoder[KeyboardButtonRequestUser]
  implicit val KeyboardButtonRequestChatDecoder: Decoder[KeyboardButtonRequestChat] =
    deriveDecoder[KeyboardButtonRequestChat]
  implicit val UserSharedDecoder: Decoder[UserShared] = deriveDecoder[UserShared]
  implicit val ChatSharedDecoder: Decoder[ChatShared] = deriveDecoder[ChatShared]

  // for v6.4 support
  implicit val editGeneralForumTopicDecoder: Decoder[EditGeneralForumTopic]     = deriveDecoder[EditGeneralForumTopic]
  implicit val closeGeneralForumTopicDecoder: Decoder[CloseGeneralForumTopic]   = deriveDecoder[CloseGeneralForumTopic]
  implicit val reopenGeneralForumTopicDecoder: Decoder[ReopenGeneralForumTopic] = deriveDecoder[ReopenGeneralForumTopic]
  implicit val hideGeneralForumTopicDecoder: Decoder[HideGeneralForumTopic]     = deriveDecoder[HideGeneralForumTopic]
  implicit val unhideGeneralForumTopicDecoder: Decoder[UnhideGeneralForumTopic] = deriveDecoder[UnhideGeneralForumTopic]
  implicit val generalForumTopicHiddenDecoder: Decoder[GeneralForumTopicHidden.type] =
    deriveDecoder[GeneralForumTopicHidden.type]
  implicit val generalForumTopicUnhiddenDecoder: Decoder[GeneralForumTopicUnhidden.type] =
    deriveDecoder[GeneralForumTopicUnhidden.type]
  implicit val writeAccessAllowedDecoder: Decoder[WriteAccessAllowed] = deriveDecoder[WriteAccessAllowed]

  // for v6.3 support
  implicit val forumTopicDecoder: Decoder[ForumTopic]                      = deriveDecoder[ForumTopic]
  implicit val forumTopicCreatedDecoder: Decoder[ForumTopicCreated]        = deriveDecoder[ForumTopicCreated]
  implicit val forumTopicClosedDecoder: Decoder[ForumTopicClosed.type]     = deriveDecoder[ForumTopicClosed.type]
  implicit val forumTopicReopenedDecoder: Decoder[ForumTopicReopened.type] = deriveDecoder[ForumTopicReopened.type]
  implicit val forumTopicEditedDecoder: Decoder[ForumTopicEdited]          = deriveDecoder[ForumTopicEdited]

  implicit val createForumTopicDecoder: Decoder[CreateForumTopic] = deriveDecoder[CreateForumTopic]
  implicit val editForumTopicDecoder: Decoder[EditForumTopic]     = deriveDecoder[EditForumTopic]
  implicit val closeForumTopicDecoder: Decoder[CloseForumTopic]   = deriveDecoder[CloseForumTopic]
  implicit val reopenForumTopicDecoder: Decoder[ReopenForumTopic] = deriveDecoder[ReopenForumTopic]
  implicit val deleteForumTopicDecoder: Decoder[DeleteForumTopic] = deriveDecoder[DeleteForumTopic]
  implicit val unpinAllForumTopicMessagesDecoder: Decoder[UnpinAllForumTopicMessages] =
    deriveDecoder[UnpinAllForumTopicMessages]
  implicit val getForumTopicIconStickersDecoder: Decoder[GetForumTopicIconStickers.type] =
    deriveDecoder[GetForumTopicIconStickers.type]

  // for v6.0 support
  implicit val webAppInfoDecoder: Decoder[WebAppInfo]                   = deriveDecoder[WebAppInfo]
  implicit val webAppDataDecoder: Decoder[WebAppData]                   = deriveDecoder[WebAppData]
  implicit val chatAdminRightsDecoder: Decoder[ChatAdministratorRights] = deriveDecoder[ChatAdministratorRights]
  implicit val sentWebAppMessageDecoder: Decoder[SentWebAppMessage]     = deriveDecoder[SentWebAppMessage]
  // for v5.1 support
  implicit val chatInviteLinkDecoder: Decoder[ChatInviteLink]       = deriveDecoder[ChatInviteLink]
  implicit val chatMemberUpdatedDecoder: Decoder[ChatMemberUpdated] = deriveDecoder[ChatMemberUpdated]

  implicit val chatJoinrequestDecoder: Decoder[ChatJoinRequest] = deriveDecoder[ChatJoinRequest]

  implicit val audioDecoder: Decoder[Audio] = deriveDecoder[Audio]

  implicit val chatDecoder: Decoder[Chat]           = deriveDecoder[Chat]
  implicit val chatPhotoDecoder: Decoder[ChatPhoto] = deriveDecoder[ChatPhoto]

  implicit val KeyboardButtonPollTypeDecoder: Decoder[KeyboardButtonPollType] = deriveDecoder[KeyboardButtonPollType]

  implicit val menuButtonDecoder: Decoder[MenuButton]                     = MenuButton.menuButtonDecoder
  implicit val contactDecoder: Decoder[Contact]                           = deriveDecoder[Contact]
  implicit val documentDecoder: Decoder[Document]                         = deriveDecoder[Document]
  implicit val fileDecoder: Decoder[File]                                 = deriveDecoder[File]
  implicit val callbackGameDecoder: Decoder[CallbackGame]                 = Decoder.const(CallbackGame)
  implicit val inlineKeyboardButtonDecoder: Decoder[InlineKeyboardButton] = deriveDecoder[InlineKeyboardButton]

  implicit val inlineKeyboardMarkupDecoder: Decoder[InlineKeyboardMarkup] = deriveDecoder[InlineKeyboardMarkup]

  implicit val keyboardButtonDecoder: Decoder[KeyboardButton] = deriveDecoder[KeyboardButton]
  implicit val locationDecoder: Decoder[Location]             = deriveDecoder[Location]

  implicit val messageEntityDecoder: Decoder[MessageEntity] = deriveDecoder[MessageEntity]

  implicit val webhookInfoDecoder: Decoder[WebhookInfo] = deriveDecoder[WebhookInfo]

  implicit val photoSizeDecoder: Decoder[PhotoSize] = deriveDecoder[PhotoSize]

  implicit val replyMarkupDecoder: Decoder[ReplyMarkup] = deriveDecoder[ReplyMarkup]

  implicit val listStickerDecoder: Decoder[List[Sticker]] = deriveDecoder[List[Sticker]]
  implicit val stickerDecoder: Decoder[Sticker]           = deriveDecoder[Sticker]

  implicit val messageDecoder: Decoder[Message]             = deriveDecoder[Message]
  implicit val arrayMessageDecoder: Decoder[Array[Message]] = Decoder.decodeArray[Message]
  implicit val eitherMessageBooleanDecoder: Decoder[Either[Message, Boolean]] = {
    val l: Decoder[Either[Message, Boolean]] = messageDecoder.map(Left.apply)
    val r: Decoder[Either[Message, Boolean]] = Decoder.decodeBoolean.map(Right.apply)
    l or r
  }
  implicit val eitherBooleanMessageDecoder: Decoder[Either[Boolean, Message]] = {
    val l: Decoder[Either[Boolean, Message]] = Decoder.decodeBoolean.map(Left.apply)
    val r: Decoder[Either[Boolean, Message]] = messageDecoder.map(Right.apply)
    l or r
  }
  implicit val messageIdDecoder: Decoder[MessageId]         = deriveDecoder[MessageId]
  implicit val callbackQueryDecoder: Decoder[CallbackQuery] = deriveDecoder[CallbackQuery]

  implicit val stickerSetDecoder: Decoder[StickerSet] = deriveDecoder[StickerSet]

  implicit val seqChatMemberDecoder: Decoder[Seq[ChatMember]] = Decoder.decodeSeq[ChatMember]
  implicit val chatMemberDecoder: Decoder[ChatMember]         = deriveDecoder[ChatMember]

  implicit val chatPermissionsDecoder: Decoder[ChatPermissions] = deriveDecoder[ChatPermissions]

  implicit val maskPositionDecoder: Decoder[MaskPosition] = deriveDecoder[MaskPosition]

  implicit val userDecoder: Decoder[User]                           = deriveDecoder[User]
  implicit val userProfilePhotosDecoder: Decoder[UserProfilePhotos] = deriveDecoder[UserProfilePhotos]
  implicit val venueDecoder: Decoder[Venue]                         = deriveDecoder[Venue]
  implicit val videoDecoder: Decoder[Video]                         = deriveDecoder[Video]
  implicit val storyDecoder: Decoder[Story.type]                    = deriveDecoder[Story.type]
  implicit val videoNoteDecoder: Decoder[VideoNote]                 = deriveDecoder[VideoNote]
  implicit val voiceDecoder: Decoder[Voice]                         = deriveDecoder[Voice]
  implicit val videoChatEndedDecoder: Decoder[VideoChatEnded]       = deriveDecoder[VideoChatEnded]
  implicit val videoChatParticipantsInvitedDecoder: Decoder[VideoChatParticipantsInvited] =
    deriveDecoder[VideoChatParticipantsInvited]
  implicit val videoChatScheduledDecoder: Decoder[VideoChatScheduled]  = deriveDecoder[VideoChatScheduled]
  implicit val videoChatStartedDecoder: Decoder[VideoChatStarted.type] = deriveDecoder[VideoChatStarted.type]

  implicit val seqGameHighScoreDecoder: Decoder[Seq[GameHighScore]] = Decoder.decodeSeq[GameHighScore]
  implicit val gameHighScoreDecoder: Decoder[GameHighScore]         = deriveDecoder[GameHighScore]
  implicit val animationDecoder: Decoder[Animation]                 = deriveDecoder[Animation]
  implicit val gameDecoder: Decoder[Game]                           = deriveDecoder[Game]

  implicit val inlineQueryDecoder: Decoder[InlineQuery]              = deriveDecoder[InlineQuery]
  implicit val chosenInlineQueryDecoder: Decoder[ChosenInlineResult] = deriveDecoder[ChosenInlineResult]

  implicit val inputContactMessageContent: Decoder[InputContactMessageContent] =
    deriveDecoder[InputContactMessageContent]
  implicit val inputVenueMessageContentDecoder: Decoder[InputVenueMessageContent] =
    deriveDecoder[InputVenueMessageContent]
  implicit val inputLocationMessageContentDecoder: Decoder[InputLocationMessageContent] =
    deriveDecoder[InputLocationMessageContent]
  implicit val inputTextMessageContentDecoder: Decoder[InputTextMessageContent] =
    deriveDecoder[InputTextMessageContent]

  implicit val labeledPriceDecoder: Decoder[LabeledPrice]       = deriveDecoder[LabeledPrice]
  implicit val invoiceDecoder: Decoder[Invoice]                 = deriveDecoder[Invoice]
  implicit val shippingAddressDecoder: Decoder[ShippingAddress] = deriveDecoder[ShippingAddress]

  implicit val pollDecoder: Decoder[Poll]             = deriveDecoder[Poll]
  implicit val pollOptionDecoder: Decoder[PollOption] = deriveDecoder[PollOption]

  implicit val shippingQueryDecoder: Decoder[ShippingQuery]         = deriveDecoder[ShippingQuery]
  implicit val orderInfoDecoder: Decoder[OrderInfo]                 = deriveDecoder[OrderInfo]
  implicit val preCheckoutQueryDecoder: Decoder[PreCheckoutQuery]   = deriveDecoder[PreCheckoutQuery]
  implicit val shippingOptionDecoder: Decoder[ShippingOption]       = deriveDecoder[ShippingOption]
  implicit val successfulPaymentDecoder: Decoder[SuccessfulPayment] = deriveDecoder[SuccessfulPayment]

  implicit val responseParametersDecoder: Decoder[ResponseParameters] = deriveDecoder[ResponseParameters]

  implicit val getUpdatesDecoder: Decoder[GetUpdates] = deriveConfiguredDecoder
  implicit val seqParsedUpdateDecoder: Decoder[Seq[ParsedUpdate]] = (c: HCursor) => {
    c.as[Seq[Json]].flatMap { jsonArray =>
      val results = jsonArray.map { json =>
        json.as[Update] match {
          case Right(update) => ParsedUpdate.Success(update)
          case Left(error) =>
            val updateId = json.hcursor.get[Long]("update_id").getOrElse(-1L)
            ParsedUpdate.Failure(updateId, error)
        }
      }
      Right(results)
    }
  }
  implicit val parsedUpdateDecoder: Decoder[ParsedUpdate] = (c: HCursor) => {
    updateDecoder(c) match {
      case Left(e) =>
        for {
          id <- c.get[Long]("update_id")
        } yield ParsedUpdate.Failure(id, e)
      case Right(value) => Right(ParsedUpdate.Success(value))
    }
  }
  implicit val updateDecoder: Decoder[Update] = deriveDecoder[Update]

  implicit val loginUrlDecoder: Decoder[LoginUrl] = deriveDecoder[LoginUrl]

  implicit def responseDecoder[T: Decoder]: Decoder[Response[T]] = deriveDecoder[Response[T]]
}

object CirceDecoders extends CirceDecoders
