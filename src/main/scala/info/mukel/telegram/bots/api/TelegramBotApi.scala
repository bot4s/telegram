package info.mukel.telegram.bots.api

import java.io.File

import info.mukel.telegram.bots.Utils
import info.mukel.telegram.bots.api.ChatAction.ChatAction
import info.mukel.telegram.bots.http.HttpClient
import info.mukel.telegram.bots.json.JsonUtils

import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

import scala.util.Failure
import scala.concurrent._
import ExecutionContext.Implicits.global


case class InvalidResponse(response: String) extends Exception

/**
 * TelegramBotApi
 *
 * Official Telegram Bot API wrapper.
 * @param token  Bot token
 */
class TelegramBotApi(token: String) {
  this: HttpClient =>    

  implicit val formats = DefaultFormats

  private val apiBaseURL = "https://api.telegram.org/bot" + token + "/"

  protected def syncApiCall(action: String, params : (String, Any)*): JValue = {
    val requestUrl = apiBaseURL + action
    val response = request(requestUrl, params : _*)
    val json = parse(response)
    if((json \ "ok").extract[Boolean])
      (json \ "result")
    else
      throw new Exception("Invalid reponse:\n" + response)
  }

  protected def apiCall(action: String, params : (String, Any)*): Future[JValue] = {
    val requestUrl = apiBaseURL + action
    for (response <- asyncRequest(requestUrl, params : _*)) yield {
      val json = parse(response)
      if((json \ "ok").extract[Boolean])
        (json \ "result")
      else
        throw new InvalidResponse(response)
    }
  }

  protected def getAs[R: Manifest](action: String, params : (String, Any)*): Future[R] = {
    for {
      json <- apiCall(action, params : _*)
    } yield JsonUtils.unjsonify[R](json)
  }

  /**
   * getMe
   * 
   * A simple method for testing your bot's auth token. Requires no parameters.
   * Returns basic information about the bot in form of a User object.
   */
  def getMe: Future[User] = getAs[User]("getMe")

  /**
   * sendMessage
   *
   * Use this method to send text messages. On success, the sent Message is returned.
   * 
   * @param chatId                 Unique identifier for the message recipient — User or GroupChat id
   * @param text 	               Text of the message to be sent
   * @param parseMode              Optional		Send "Markdown", if you want Telegram apps to show bold, italic and inline URLs in your bot's message. For the moment, only Telegram for Android supports this.
   * @param disableWebPagePreview  Optional 	Disables link previews for links in this message
   * @param replyToMessageId       Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup            Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   *	
   * Using markdown
   * The Bot API supports very basic markdown. You can use bold and italic text, as well as inline links in your bots' messages. Telegram clients will render them accordingly (for the moment this is only supported in Telegram for Android and Telegram Web, but all official clients will catch up soon).
   * For this to work, make sure to pass Markdown in the parse_mode field when using sendMessage. Use the following syntax in your message:
   * 
   * *bold text*
   * _italic text_
   * [text](URL)
   *
   * Note that Telegram clients will display an alert to the user before opening an inline link (‘Open this link?’ together with the full URL).
   * 
   * You can also use backticks for fixed-width and pre-formatted code:
   * 
   * `inline fixed-width code`
   * ```pre-formatted fixed-width code block```
   */
  def sendMessage(chatId                : Int,
                  text                  : String,
                  parseMode             : Option[String] = None,
                  disableWebPagePreview : Option[Boolean] = None,
                  replyToMessageId      : Option[Int] = None,
                  replyMarkup           : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendMessage",
      "chat_id"                   -> chatId,
      "text"                      -> text,
      "parse_mode"                -> parseMode,
      "disable_web_page_preview"  -> disableWebPagePreview,
      "reply_to_message_id"       -> replyToMessageId,
      "reply_markup"              -> (replyMarkup map JsonUtils.jsonify))
  }

  /**
   *forwardMessage
   *
   * Use this method to forward messages of any kind. On success, the sent Message is returned.
   *
   * @param chatId      Unique identifier for the message recipient — User or GroupChat id
   * @param fromChatId  Unique identifier for the chat where the original message was sent — User or GroupChat id
   * @param messageId   Unique message identifier
   */
  def forwardMessage(chatId     : Int,
                     fromChatId : Int,
                     messageId  : Int): Future[Message] =
  {
    getAs[Message]("forwardMessage",
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
   *
   * @param chatId  Unique identifier for the message recipient — User or GroupChat id
   * @param action   Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data.
   */
  def sendChatAction(chatId : Int,
                     action : ChatAction): Unit = {
    apiCall("sendChatAction",
      "chat_id" -> chatId,
      "action"  -> action)
  }

  /**
   * sendLocation
   *
   * Use this method to send point on the map. On success, the sent Message is returned.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param latitude          Latitude of location
   * @param longitude         Longitude of location
   * @param replyToMessageId  Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup       Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendLocation(chatId           : Int,
                   latitude         : Float,
                   longitude        : Float,
                   replyToMessageId : Option[Int] = None,
                   replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendLocation",
      "chat_id"             -> chatId,
      "latitude"            -> latitude,
      "longitude"           -> longitude,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }


  /**
   * getUserProfilePhotos
   *
   * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
   *
   * @param userId  Unique identifier of the target user
   * @param offset  Optional 	Sequential number of the first photo to be returned. By default, all photos are returned.
   * @param limit   Optional 	Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
   */
  def getUserProfilePhotos(userId : Int,
                           offset : Option[Int] = None,
                           limit  : Option[Int] = None): Future[UserProfilePhotos] = {
    getAs[UserProfilePhotos]("getUserProfilePhotos",
      "user_id" -> userId,
      "offset"  -> offset,
      "limit"   -> limit)
  }

  /**
   * sendPhotoId
   *
   * Use this method to send photos. On success, the sent Message is returned.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param photoFile         Photo to send. You can either pass a fileId as String to resend a photo that is already on the Telegram servers, or upload a new photo using multipart/form-data.
   * @param caption           Optional 	Photo caption (may also be used when resending photos by fileId).
   * @param replyToMessageId  Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup       Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendPhoto(chatId           : Int,
                photoFile        : InputFile,
                caption          : Option[String] = None,
                replyToMessageId : Option[Int] = None,
                replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendPhoto",
      "chat_id"             -> chatId,
      "photo"               -> photoFile,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  def sendPhotoId(chatId           : Int,
                  photoId          : String,
                  caption          : Option[String] = None,
                  replyToMessageId : Option[Int] = None,
                  replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendPhoto",
      "chat_id"             -> chatId,
      "photo"               -> photoId,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  /**
   * sendAudio
   *
   * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .mp3 format. On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
   * For backward compatibility, when the fields title and performer are both empty and the mime-type of the file to be sent is not audio/mpeg, the file will be sent as a playable voice message. For this to work, the audio must be in an .ogg file encoded with OPUS. This behavior will be phased out in the future. For sending voice messages, use the sendVoice method instead.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param audioFile         Audio file to send. You can either pass a fileId as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
   * @param duration          Optional 	Duration of sent audio in seconds
   * @param performer         Optional. Performer of the audio as defined by sender or by audio tags
   * @param title             Optional. Title of the audio as defined by sender or by audio tags
   * @param replyToMessageId  Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup       Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendAudio(chatId           : Int,
                audioFile        : InputFile,
                duration         : Option[Int] = None,
                performer        : Option[String] = None,
                title            : Option[String] = None,
                replyToMessageId : Option[Int] = None,
                replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendAudio",
      "chat_id"             -> chatId,
      "audio"               -> audioFile,      
      "duration"            -> duration,
      "performer"           -> performer,
      "title"               -> title,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  def sendAudioId(chatId           : Int,
                  audioId          : String,
                  duration         : Option[Int] = None,
                  performer        : Option[String] = None,
                  title            : Option[String] = None,
                  replyToMessageId : Option[Int] = None,
                  replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendAudio",
      "chat_id"             -> chatId,
      "audio"               -> audioId,
      "duration"            -> duration,
      "performer"           -> performer,
      "title"               -> title,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  /**
   * sendVoice
   *
   * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param audioFile         Audio file to send. You can either pass a file_id as String to resend an audio that is already on the Telegram servers, or upload a new audio file using multipart/form-data.
   * @param duration          Optional  Duration of sent audio in seconds
   * @param replyToMessageId  Optional  If the message is a reply, ID of the original message
   * @param replyMarkup       Optional  Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendVoice(chatId           : Int,
                audioFile        : InputFile,
                duration         : Option[Int] = None,
                performer        : Option[String] = None,
                title            : Option[String] = None,
                replyToMessageId : Option[Int] = None,
                replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendVoice",
      "chat_id"             -> chatId,
      "audio"               -> audioFile,
      "duration"            -> duration,
      "performer"           -> performer,
      "title"               -> title,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  def sendVoiceId(chatId           : Int,
                  audioId          : String,
                  duration         : Option[Int] = None,
                  performer        : Option[String] = None,
                  title            : Option[String] = None,
                  replyToMessageId : Option[Int] = None,
                  replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendVoice",
      "chat_id"             -> chatId,
      "audio"               -> audioId,
      "duration"            -> duration,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  } 


  /**
   * sendDocument
   *
   * Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param documentFile      File to send. You can either pass a fileId as String to resend a file that is already on the Telegram servers, or upload a new file using multipart/form-data.
   * @param replyToMessageId 	Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup       Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendDocument(chatId           : Int,
                   documentFile     : InputFile,
                   replyToMessageId : Option[Int] = None,
                   replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendDocument",
      "chat_id"             -> chatId,
      "document"            -> documentFile,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }
  def sendDocumentId(chatId           : Int,
                     documentId       : String,
                     replyToMessageId : Option[Int] = None,
                     replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendDocument",
      "chat_id"             -> chatId,
      "document"            -> documentId,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  /**
   * sendSticker
   *
   * Use this method to send .webp stickers. On success, the sent Message is returned.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param stickerFile       Sticker to send. You can either pass a fileId as String to resend a sticker that is already on the Telegram servers, or upload a new sticker using multipart/form-data.
   * @param replyToMessageId 	Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup       Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendSticker(chatId           : Int,
                  stickerFile      : InputFile,
                  replyToMessageId : Option[Int] = None,
                  replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendSticker",
      "chat_id"             -> chatId,
      "sticker"             -> stickerFile,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  def sendStickerId(chatId           : Int,
                    stickerId        : String,
                    replyToMessageId : Option[Int] = None,
                    replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendSticker",
      "chat_id"             -> chatId,
      "sticker"             -> stickerId,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  /**
   * sendVideo
   *
   * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.
   *
   * @param chatId            Unique identifier for the message recipient — User or GroupChat id
   * @param videoFile         Video to send. You can either pass a fileId as String to resend a video that is already on the Telegram servers, or upload a new video file using multipart/form-data.
   * @param duration          Optional 	Duration of sent video in seconds
   * @param caption           Optional 	Video caption (may also be used when resending videos by fileId).
   * @param replyToMessageId  Optional 	If the message is a reply, ID of the original message
   * @param replyMarkup       Optional 	Additional interface options. A JSON-serialized object for a custom reply keyboard, instructions to hide keyboard or to force a reply from the user.
   */
  def sendVideo(chatId           : Int,
                videoFile        : InputFile,
                duration         : Option[Int] = None,
                caption          : Option[String] = None,
                replyToMessageId : Option[Int] = None,
                replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendVideo",
      "chat_id"             -> chatId,
      "video"               -> videoFile,
      "duration"            -> duration,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  def sendVideoId(chatId           : Int,
                  videoId          : String,
                  duration         : Option[Int] = None,
                  caption          : Option[String] = None,
                  replyToMessageId : Option[Int] = None,
                  replyMarkup      : Option[ReplyMarkup] = None): Future[Message] =
  {
    getAs[Message]("sendAudio",
      "chat_id"             -> chatId,
      "video"               -> videoId,
      "duration"            -> duration,
      "caption"             -> caption,
      "reply_to_message_id" -> replyToMessageId,
      "reply_markup"        -> (replyMarkup map JsonUtils.jsonify))
  }

  /**
   * getUpdates
   *
   * Use this method to receive incoming updates using long polling (wiki). An Array of Update objects is returned.
   *
   * @param offset   Optional 	Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
   * @param limit    Optional 	Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
   * @param timeout  Optional 	Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
   *   Notes
   *     1. This method will not work if an outgoing webhook is set up.
   *     2. In order to avoid getting duplicate updates, recalculate offset after each server response.
   */
  def getUpdates(offset  : Option[Int] = None,
                 limit   : Option[Int] = None,
                 timeout : Option[Int] = None): Future[Array[Update]] = {
    getAs[Array[Update]]("getUpdates",
      "offset"  -> offset,
      "limit"   -> limit,
      "timeout" -> timeout)
  }

  /**
   * setWebhook
   *
   * Use this method to specify a url and receive incoming updates via an outgoing webhook. Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a reasonable amount of attempts.
   * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. www.example.com/<token>. Since nobody else knows your bot‘s token, you can be pretty sure it’s us.
   *
   * @param url          Optional HTTPS url to send updates to. Use an empty string to remove webhook integration
   * @param certificate  Optional	Upload your public key certificate so that the root certificate in use can be checked
   *   Notes
   *     1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
   *     2. To use a self-signed certificate, you need to upload your public key certificate using certificate parameter. Please upload as InputFile, sending a String will not work.
   *     3. Ports currently supported for Webhooks: 443, 80, 88, 8443.
   */
  def setWebhook(url         : Option[String],
                 certificate : Option[InputFile] = None): Unit = {
    syncApiCall("setWebhook",
      "url"         -> url,
      "certificate" -> certificate)
  }

  def asyncSetWebhook(url         : Option[String],
                      certificate : Option[InputFile] = None): Unit = {
    apiCall("setWebhook",
      "url"         -> url,
      "certificate" -> certificate)
  }

  /**
   * Use this method to get basic info about a file and prepare it for downloading.
   * For the moment, bots can download files of up to 20MB in size. On success, a File object is returned. The file can then be downloaded via the
   * link https://api.telegram.org/file/bot<token>/<file_path>, where <file_path> is taken from the response. It is guaranteed that the link will be valid for
   * at least 1 hour. When the link expires, a new one can be requested by calling getFile again.
   *
   * @param fileId File identifier to get info about
   */
  def getFile(fileId: String) = getAs[File]("getFile", "file_id" -> fileId)
}
