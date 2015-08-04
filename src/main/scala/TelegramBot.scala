
/*
 Telegram Bot API Wrapper for Scala

 Currently implemented:
   DONE getMe
   DONE sendMessage
   DONE forwardMessage
   DONE sendPhoto
   DONE sendAudio
   DONE sendDocument
   DONE sendSticker
   DONE sendVideo
   DONE sendLocation
   DONE sendChatAction
   DONE getUserProfilePhotos
   DONE getUpdates
   DONE setWebhoDONE   !!! The setWebhoDONE method is implemented but the embedded webserver isn't!!!

 Missing (not yet implemented):
   ForceReply
   Keyboard markups
*/

import java.io.File
import java.nio.file.{Paths, Files}

import Status.Status
import org.json4s._
import org.json4s.native.JsonMethods._

import scalaj.http._

class TelegramBot(token: String) {

  private val apiBaseURL = "https://api.telegram.org/bot" + token + "/"

  protected def getJSON(action: String, options : (String, Any)*): JValue = {

    val (rawFiles, parameters) = options partition {
      case (_, _:File) => true
      case _ => false
    }

    // Skip Nones and stringify the rest
    val p = parameters filter {
      case (_, None) => false
      case _ => true
    } map {
      case (a, Some(b)) => (a, b.toString)
      case (a, b) => (a, b.toString)
    }

    var query = Http(apiBaseURL + action).params(p).timeout(10000, 20000)

    val files = rawFiles map { case (a, b:File) => (a, b) }

    implicit val formats = DefaultFormats

    for ((id, file) <- files) {
      val byteArray = Files.readAllBytes(Paths.get(file.getAbsolutePath))
      val fileName = file.getName
      val mimeType = MimeTypes.fromFileName(fileName)
      query = query.postMulti(MultiPart(id, fileName, mimeType, byteArray))
    }

    println("Query: " + query.toString)

    val response = query.asString

    parseOpt(response.body) match {
      case Some(json) if (json \ "ok").extract[Boolean] =>
          (json \ "result")

      case _ => throw new Exception("Invalid reponse:\n" + response.body)
    }
  }

  protected def getAs[R: Manifest](action: String, options : (String, Any)*): R = {
    implicit val formats = DefaultFormats
    getJSON(action, options : _*).extract[R]
  }

  protected def getAsOption[R: Manifest](action: String, options : (String, Any)*): Option[R] = {
    implicit val formats = DefaultFormats
    getJSON(action, options : _*).extractOpt[R]
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
  def sendMessage(chat_id: Int,
                  text : String,
                  disable_web_page_preview : Option[Boolean] = None,
                  reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendMessage",
      "chat_id"                   -> chat_id,
      "text"                      -> text,
      "disable_web_page_preview"  -> disable_web_page_preview,
      "reply_to_message_id"       -> reply_to_message_id)
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
  def forwardMessage(chat_id: Int,
                     from_chat_id : Int,
                     message_id : Int): Option[Message] =
  {
    getAsOption[Message]("forwardMessage",
      "chat_id"      -> chat_id,
      "from_chat_id" -> from_chat_id,
      "message_id"   -> message_id)
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
  def sendChatAction(chat_id: Int,
                     action: Status): Unit = {
    getJSON("sendChatAction",
      "chat_id" -> chat_id,
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
  def sendLocation(chat_id: Int,
                   latitude: Float,
                   longitude: Float,
                   reply_to_message_id: Option[Int] = None): Message =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAs[Message]("sendLocation",
      "chat_id"             -> chat_id,
      "latitude"            -> latitude,
      "longitude"           -> longitude,
      "reply_to_message_id" -> reply_to_message_id)
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
  def getUserProfilePhotos(user_id: Int,
                           offset: Option[Int] = None,
                           limit: Option[Int] = None): UserProfilePhotos = {

    getAs[UserProfilePhotos]("getUserProfilePhotos",
      "user_id" -> user_id,
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
  def sendPhotoId(chat_id: Int,
                  photo_id : String,
                  caption : Option[String] = None,
                  reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendPhoto",
      "chat_id"             -> chat_id,
      "photo"               -> photo_id,
      "caption"             -> caption,
      "reply_to_message_id" -> reply_to_message_id)
  }

  def sendPhoto(chat_id: Int,
                photo_file : File,
                caption : Option[String] = None,
                reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendPhoto",
      "chat_id"             -> chat_id,
      "photo"               -> photo_file,
      "caption"             -> caption,
      "reply_to_message_id" -> reply_to_message_id)
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
  def sendAudio(chat_id: Int,
                audio_file : File,
                duration : Option[Int] = None,
                reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendAudio",
      "chat_id"             -> chat_id,
      "audio"               -> audio_file,
      "duration"            -> duration,
      "reply_to_message_id" -> reply_to_message_id)
  }

  def sendAudioId(chat_id: Int,
                  audio_id : Int,
                  duration : Option[Int] = None,
                  reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendAudio",
      "chat_id"             -> chat_id,
      "audio"               -> audio_id,
      "duration"            -> duration,
      "reply_to_message_id" -> reply_to_message_id)
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
  def sendDocument(chat_id: Int,
                   document_file : File,
                   reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendDocument",
      "chat_id"             -> chat_id,
      "document"            -> document_file,
      "reply_to_message_id" -> reply_to_message_id)
  }

  def sendDocumentId(chat_id: Int,
                     document_id : Int,
                     reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendDocument",
      "chat_id"             -> chat_id,
      "document"            -> document_id,
      "reply_to_message_id" -> reply_to_message_id)
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
  def sendSticker(chat_id: Int,
                  sticker_file : File,
                  reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendSticker",
      "chat_id"             -> chat_id,
      "sticker"             -> sticker_file,
      "reply_to_message_id" -> reply_to_message_id)
  }

  def sendStickerId(chat_id: Int,
                    sticker_id : Int,
                    reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendSticker",
      "chat_id"             -> chat_id,
      "sticker"             -> sticker_id,
      "reply_to_message_id" -> reply_to_message_id)
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
  def sendVideo(chat_id: Int,
                video_file : File,
                duration : Option[Int] = None,
                caption : Option[String] = None,
                reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendVideo",
      "chat_id"             -> chat_id,
      "video"               -> video_file,
      "duration"            -> duration,
      "caption"             -> caption,
      "reply_to_message_id" -> reply_to_message_id)
  }

  def sendVideoId(chat_id: Int,
                  video_id : Int,
                  duration : Option[Int] = None,
                  caption : Option[String] = None,
                  reply_to_message_id : Option[Int] = None): Option[Message] =
  // reply_markup : Option[ Either[ReplyKeyboardMarkup, ReplyKeyboardHide, ForceReply] ])
  {
    getAsOption[Message]("sendAudio",
      "chat_id"             -> chat_id,
      "video"               -> video_id,
      "duration"            -> duration,
      "caption"             -> caption,
      "reply_to_message_id" -> reply_to_message_id)
  }
}
