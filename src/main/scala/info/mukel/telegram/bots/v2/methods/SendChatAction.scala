package info.mukel.telegram.bots.v2.methods

import info.mukel.telegram.bots.v2.methods.ChatAction.ChatAction

/** sendChatAction
  *
  * Use this method when you need to tell the user that something is happening on the bot's side.
  * The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
  * Example: The ImageBot needs some time to process a request and upload the image.
  * Instead of sending a text message along the lines of “Retrieving image, please wait…”, the bot may use sendChatAction with action = upload_photo.
  * The user will see a “sending photo” status for the bot.
  * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
  *
  * @param chatId  Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param action  String	Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files, upload_document for general files, find_location for location data.
  */
case class SendChatAction(
                         chatId : Long,
                         action : ChatAction
                         ) extends ApiRequestJson[Boolean]

/** ChatAction
  *
  * Type of action to broadcast.
  *
  * Choose one, depending on what the user is about to receive:
  *   typing for text messages,
  *   upload_photo for photos,
  *   record_video or upload_video for videos,
  *   record_audio or upload_audio for audio files,
  *   upload_document for general files,
  *   find_location for location data.
  */
object ChatAction extends Enumeration {
  type ChatAction = Value
  val Typing = Value("typing")
  val UploadPhoto = Value("upload_photo")
  val RecordVideo = Value("record_video")
  val UploadVideo = Value("upload_video")
  val RecordAudio = Value("record_audio")
  val UploadAudio = Value("upload_audio")
  val UploadDocument = Value("upload_document")
  val FindLocation = Value("find_location")
}