
/*
 Telegram Bot API Wrapper for Scala

 Currently implemented:
   DONE getMe TESTED
   DONE sendMessage TESTED
   DONE forwardMessage
   DONE sendPhoto TESTED
   DONE sendAudio TESTED
   DONE sendDocument TESTED
   DONE sendSticker TESTED
   DONE sendVideo TESTED
   DONE sendLocation
   DONE sendChatAction TESTED
   DONE getUserProfilePhotos
   DONE getUpdates TESTED
   DONE setWebhoDONE   !!! The setWebhoDONE method is implemented but the embedded webserver isn't!!!

 Missing (not yet implemented):
   ForceReply
   Keyboard markups
*/

import java.io.File
import java.net.URLConnection
import java.nio.file.{Paths, Files}

import ChatAction.ChatAction
import org.json4s._
import org.json4s.native.JsonMethods._

import scalaj.http._

class TelegramBotAPI(token: String) {
  this: HttpClient =>

  private val apiBaseURL = "https://api.telegram.org/bot" + token + "/"

  protected def getJson(action: String, options : (String, Any)*): JValue = {
    val requestUrl = apiBaseURL + action
    val response = request(requestUrl, options : _*)

    implicit val formats = DefaultFormats
    // TODO: Rethink the error handling, right now ok=false is considered an error!!!
    parseOpt(response) match {
      case Some(json) if (json \ "ok").extract[Boolean] =>
        val result = (json \ "result") transformField { case (key, value) => (Utils.underscoreToCamel(key), value) }
        println(result)
        result
      case _ => throw new Exception("Invalid reponse:\n" + response)
    }
  }

  protected def getAs[R: Manifest](action: String, options : (String, Any)*): R = {
    implicit val formats = DefaultFormats
    getJson(action, options : _*).extract[R]
  }

  protected def getAsOption[R: Manifest](action: String, options : (String, Any)*): Option[R] = {
    implicit val formats = DefaultFormats
    getJson(action, options : _*).extractOpt[R]
  }

  /**
   * getMe
   * A simple method for testing your bot's auth token. Requires no parameters.
   * Returns basic information about the bot in form of a User object.
   */
  def getMe: User = getAs[User]("getMe")

  /**
   * sendMessage
   *
   * Use this method to send text messages. On success, the sent Message is returned.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * text 	String 	Yes 	Text of the message to be sent
   * disable_web_page_preview 	Boolean 	Optional 	Disables link previews for links in this message
   * reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   * reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options.
   *     A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendMessage(chatId: Int,
                  text : String,
                  disableWebPagePreview : Option[Boolean] = None,
                  replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendMessage",
      "chat_id"                   -> chatId,
      "text"                      -> text,
      "disable_web_page_preview"  -> disableWebPagePreview,
      "reply_to_message_id"       -> replyToMessageId)
  }

  /**
   *forwardMessage
   *
   * Use this method to forward messages of any kind. On success, the sent Message is returned.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * from_chat_id 	Integer 	Yes 	Unique identifier for the chat where the original message was sent — User or GroupChat id
   * message_id 	Integer 	Yes 	Unique message identifier
   */
  def forwardMessage(chatId: Int,
                     fromChatId : Int,
                     messageId : Int): Option[Message] =
  {
    getAsOption[Message]("forwardMessage",
      "chat_id"      -> chatId,
      "from_chat_id" -> fromChatId,
      "message_id"   -> messageId)
  }

  /**
   * sendChatAction
   *
   * Use this method when you need to tell the user that something is happening on the bot's side.
   * The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
   * Example: The ImageBot needs some time to process a request and upload the image.
   * Instead of sending a text message along the lines of “Retrieving image, please wait…”, the bot may use sendChatAction with action = upload_photo.
   * The user will see a “sending photo” status for the bot.
   * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * action 	String 	Yes 	Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data.
   */
  def sendChatAction(chatId: Int,
                     action: ChatAction): Unit = {
    getJson("sendChatAction",
      "chat_id" -> chatId,
      "action"  -> action)
  }

  /**
   * sendLocation
   *
   * Use this method to send point on the map. On success, the sent Message is returned.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * latitude 	Float number 	Yes 	Latitude of location
   * longitude 	Float number 	Yes 	Longitude of location
   * reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   * reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options.
   * A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendLocation(chatId: Int,
                   latitude: Float,
                   longitude: Float,
                   replyToMessageId: Option[Int] = None): Message =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAs[Message]("sendLocation",
      "chat_id"             -> chatId,
      "latitude"            -> latitude,
      "longitude"           -> longitude,
      "reply_to_message_id" -> replyToMessageId)
  }


  /**
   * getUserProfilePhotos
   *
   * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
   * Parameters 	Type 	Required 	Description
   * user_id 	Integer 	Yes 	Unique identifier of the target user
   * offset 	Integer 	Optional 	Sequential number of the first photo to be returned. By default, all photos are returned.
   * limit 	Integer 	Optional 	Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
   */
  def getUserProfilePhotos(userId: Int,
                           offset: Option[Int] = None,
                           limit: Option[Int] = None): UserProfilePhotos = {

    getAs[UserProfilePhotos]("getUserProfilePhotos",
      "user_id" -> userId,
      "offset"  -> offset,
      "limit"   -> limit)
  }

  /**
   * sendPhotoId
   *
   * Use this method to send photos. On success, the sent Message is returned.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * photo 	InputFile or String 	Yes 	Photo to send. You can either pass a file_id as String to resend a photo that is already on the Telegram servers, or upload a new photo using multipart/form-data.
   * caption 	String 	Optional 	Photo caption (may also be used when resending photos by file_id).
   * reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   * reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendPhoto(chatId: Int,
                photoFile : File,
                caption : Option[String] = None,
                replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendPhoto",
      "chat_id"             -> chatId,
      "photo"               -> photoFile,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId)
  }

  def sendPhotoId(chatId: Int,
                  photoId : String,
                  caption : Option[String] = None,
                  replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendPhoto",
      "chat_id"             -> chatId,
      "photo"               -> photoId,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId)
  }

  /**
   * sendAudio
   *
   * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
   * Parameters 	Type 	Required 	Description
   *  chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   *  audio 	InputFile or String 	Yes 	Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
   *  duration 	Integer 	Optional 	Duration of sent audio in seconds
   * reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   * reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendAudio(chatId: Int,
                audioFile : File,
                duration : Option[Int] = None,
                replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendAudio",
      "chat_id"             -> chatId,
      "audio"               -> audioFile,
      "duration"            -> duration,
      "reply_to_message_id" -> replyToMessageId)
  }

  def sendAudioId(chatId: Int,
                  audioId : String,
                  duration : Option[Int] = None,
                  replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendAudio",
      "chat_id"             -> chatId,
      "audio"               -> audioId,
      "duration"            -> duration,
      "reply_to_message_id" -> replyToMessageId)
  }

  /**
   * sendDocument
   *
   * Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
   * Parameters 	Type 	Required 	Description
   *  chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   *  document 	InputFile or String 	Yes 	File to send. You can either pass a file_id as String to resend a file that is already on the Telegram servers, or upload a new file using multipart/form-data.
   *  reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   *  reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendDocument(chatId: Int,
                   documentFile : File,
                   replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendDocument",
      "chat_id"             -> chatId,
      "document"            -> documentFile,
      "reply_to_message_id" -> replyToMessageId)
  }

  def sendDocumentId(chatId: Int,
                     documentId : String,
                     replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendDocument",
      "chat_id"             -> chatId,
      "document"            -> documentId,
      "reply_to_message_id" -> replyToMessageId)
  }

  /**
   * sendSticker
   *
   * Use this method to send .webp stickers. On success, the sent Message is returned.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * sticker 	InputFile or String 	Yes 	Sticker to send. You can either pass a file_id as String to resend a sticker that is already on the Telegram servers, or upload a new sticker using multipart/form-data.
   * reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   * reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendSticker(chatId: Int,
                  stickerFile : File,
                  replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendSticker",
      "chat_id"             -> chatId,
      "sticker"             -> stickerFile,
      "reply_to_message_id" -> replyToMessageId)
  }

  def sendStickerId(chatId: Int,
                    stickerId : String,
                    replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendSticker",
      "chat_id"             -> chatId,
      "sticker"             -> stickerId,
      "reply_to_message_id" -> replyToMessageId)
  }

  /**
   * sendVideo
   *
   * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
   * Parameters 	Type 	Required 	Description
   * chat_id 	Integer 	Yes 	Unique identifier for the message recipient — User or GroupChat id
   * video 	InputFile or String 	Yes 	Video to send. You can either pass a file_id as String to resend a video that is already on the Telegram servers, or upload a new video file using multipart/form-data.
   * duration 	Integer 	Optional 	Duration of sent video in seconds
   * caption 	String 	Optional 	Video caption (may also be used when resending videos by file_id).
   * reply_to_message_id 	Integer 	Optional 	If the message is a reply, ID of the original message
   * reply_markup 	ReplyKeyboardMarkup or ReplyKeyboardHide or ForceReply 	Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendVideo(chatId: Int,
                videoFile : File,
                duration : Option[Int] = None,
                caption : Option[String] = None,
                replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendVideo",
      "chat_id"             -> chatId,
      "video"               -> videoFile,
      "duration"            -> duration,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId)
  }

  def sendVideoId(chatId: Int,
                  videoId : String,
                  duration : Option[Int] = None,
                  caption : Option[String] = None,
                  replyToMessageId : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendAudio",
      "chat_id"             -> chatId,
      "video"               -> videoId,
      "duration"            -> duration,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId)
  }
}
