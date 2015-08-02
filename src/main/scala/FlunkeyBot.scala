import com.sun.corba.se.impl.orb.ParserTable.TestLegacyORBSocketFactory

import scala.collection.mutable
import scalaj.http._
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._

/**
 * User
 *
 * This object represents a Telegram user or bot.
 * Field 	Type 	Description
 * id 	Integer 	Unique identifier for this user or bot
 * first_name 	String 	User‘s or bot’s first namenc
 * last_name 	String 	Optional. User‘s or bot’s last name
 * username 	String 	Optional. User‘s or bot’s username
 */
case class User(
                 id         : Int,
                 first_name : String,
                 last_name  : Option[String] = None,
                 username   : Option[String] = None
                 )

/**
 * GroupChat
 *
 * This object represents a group chat.
 * Field 	Type 	Description
 * id 	Integer 	Unique identifier for this group chat
 * title 	String 	Group name
 */
case class GroupChat(
                      id    : Int,
                      title : String
                      )


// TODO: Use something like Either[User, GroupChat] instead (causes problems with the JSON extractor/deserializer)
case class UserOrGroupChat(id : Int)

/**
 * UserProfilePhotos
 *
 * This object represent a user's profile pictures.
 * Field 	Type 	Description
 * total_count 	Integer 	Total number of profile pictures the target user has
 * photos 	Array of Array of PhotoSize 	Requested profile pictures (in up to 4 sizes each)
 */
case class UserProfilePhotos(
  total_count : Int,
  photos      : Array[Array[PhotoSize]]
)


/**
 * PhotoSize
 *
 * This object represents one size of a photo or a file / sticker thumbnail.
 * Field 	Type 	Description
 * file_id 	String 	Unique identifier for this file
 * width 	Integer 	Photo width
 * height 	Integer 	Photo height
 * file_size 	Integer 	Optional. File size
 */
case class PhotoSize(
  file_id   : String,
  width     : Int,
  height    : Int,
  file_size : Option[Int] = None
)


/**
 * ReplyKeyboardMarkup
 *
 * This object represents a custom keyboard with reply options (see Introduction to bots for details and examples).
 * Field 	Type 	Description
 * keyboard 	Array of Array of String 	Array of button rows, each represented by an Array of Strings
 * resize_keyboard 	Boolean 	Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
 * one_time_keyboard 	Boolean 	Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
 * selective 	Boolean 	Optional. Use this parameter if you want to show the keyboard to specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
 *
 * Example: A user requests to change the bot‘s language, bot replies to the request with a keyboard to select the new language. Other users in the group don’t see the keyboard.
 */
case class ReplyKeyboardMarkup()

/**
 * Audio
 *
 * This object represents an audio file (voice note).
 * Field 	Type 	Description
 * file_id 	String 	Unique identifier for this file
 * duration 	Integer 	Duration of the audio in seconds as defined by sender
 * mime_type 	String 	Optional. MIME type of the file as defined by sender
 * file_size 	Integer 	Optional. File size
 */
case class Audio(
                  file_id   : String,
                  duration  : Int,
                  mime_type : Option[String] = None,
                  file_size : Option[Int] = None
                  )


/**
 * Document
 *
 * This object represents a general file (as opposed to photos and audio files).
 * Field 	Type 	Description
 * file_id 	String 	Unique file identifier
 * thumb 	PhotoSize 	Optional. Document thumbnail as defined by sender
 * file_name 	String 	Optional. Original filename as defined by sender
 * mime_type 	String 	Optional. MIME type of the file as defined by sender
 * file_size 	Integer 	Optional. File size
 */
case class Document(
                     file_id   : String,
                     thumb     : Option[PhotoSize] = None,
                     file_name : Option[String] = None,
                     mime_type : Option[String] = None,
                     file_size : Option[Int] = None
                     )

/**
 * Sticker
 *
 * This object represents a sticker.
 * Field 	Type 	Description
 * file_id 	String 	Unique identifier for this file
 * width 	Integer 	Sticker width
 * height 	Integer 	Sticker height
 * thumb 	PhotoSize 	Optional. Sticker thumbnail in .webp or .jpg format
 * file_size 	Integer 	Optional. File size
 */
case class Sticker(
                    file_id   : String,
                    width     : Int,
                    height    : Int,
                    thumb     : Option[PhotoSize] = None,
                    file_size : Option[Int] = None
                    )

/**
 * Video
 *
 * This object represents a video file.
 * Field 	Type 	Description
 * file_id 	String 	Unique identifier for this file
 * width 	Integer 	Video width as defined by sender
 * height 	Integer 	Video height as defined by sender
 * duration 	Integer 	Duration of the video in seconds as defined by sender
 * thumb 	PhotoSize 	Optional. Video thumbnail
 * mime_type 	String 	Optional. Mime type of a file as defined by sender
 * file_size 	Integer 	Optional. File size
 */
case class Video(
                  file_id   : String,
                  width     : Int,
                  height    : Int,
                  duration  : Int,
                  thumb     : Option[PhotoSize] = None,
                  mime_type : Option[String] = None,
                  file_size : Option[Int] = None
                  )


/**
 * Contact
 *
 * This object represents a phone contact.
 * Field 	Type 	Description
 * phone_number 	String 	Contact's phone number
 * first_name 	String 	Contact's first name
 * last_name 	String 	Optional. Contact's last name
 * user_id 	Integer 	Optional. Contact's user identifier in Telegram
 */
case class Contact(
                    phone_number : String,
                    first_name   : String,
                    last_name    : Option[String] = None,
                    user_id      : Option[Int] = None
                    )

/**
 * Location
 *
 * This object represents a point on the map.
 * Field 	Type 	Description
 * longitude 	Float 	Longitude as defined by sender
 * latitude 	Float 	Latitude as defined by sender
 */
case class Location(
                     longitude : Double,
                     latitude  : Double
                     )

/**
 * Message
 *
 * This object represents a message.
 * Field 	Type 	Description
 * message_id 	Integer 	Unique message identifier
 * from 	User 	Sender
 * date 	Integer 	Date the message was sent in Unix time
 * chat 	User or GroupChat 	Conversation the message belongs to — user in case of a private message, GroupChat in case of a group
 * forward_from 	User 	Optional. For forwarded messages, sender of the original message
 * forward_date 	Integer 	Optional. For forwarded messages, date the original message was sent in Unix time
 * reply_to_message 	Message 	Optional. For replies, the original message. Note that the Message object in this field will not contain further reply_to_message fields even if it itself is a reply.
 * text 	String 	Optional. For text messages, the actual UTF-8 text of the message
 * audio 	Audio 	Optional. Message is an audio file, information about the file
 * document 	Document 	Optional. Message is a general file, information about the file
 * photo 	Array of PhotoSize 	Optional. Message is a photo, available sizes of the photo
 * sticker 	Sticker 	Optional. Message is a sticker, information about the sticker
 * video 	Video 	Optional. Message is a video, information about the video
 * caption 	String 	Optional. Caption for the photo or video
 * contact 	Contact 	Optional. Message is a shared contact, information about the contact
 * location 	Location 	Optional. Message is a shared location, information about the location
 * new_chat_participant 	User 	Optional. A new member was added to the group, information about them (this member may be bot itself)
 * left_chat_participant 	User 	Optional. A member was removed from the group, information about them (this member may be bot itself)
 * new_chat_title 	String 	Optional. A group title was changed to this value
 * new_chat_photo 	Array of PhotoSize 	Optional. A group photo was change to this value
 * delete_chat_photo 	True 	Optional. Informs that the group photo was deleted
 * group_chat_created 	True 	Optional. Informs that the group has been created
 */
case class Message(
                    message_id            : Int,
                    from                  :	User,
                    date                  : Int,

                    // TODO: This is a workaround to handle the limitations of the JSON deserialization.
                    // The correct type should be Either[User, GroupChat]

                    chat                  :	UserOrGroupChat,

                    forward_from          :	Option[User] = None,
                    forward_date          : Option[Int] = None,
                    reply_to_message      : Option[Message] = None,
                    text                  : Option[String] = None,
                    audio                 : Option[Audio] = None,
                    document              : Option[Document] = None,
                    photo                 : Option[Array[PhotoSize]] = None,
                    sticker               : Option[Sticker] = None,
                    video                 : Option[Video] = None,
                    caption               : Option[String] = None,
                    contact               : Option[Contact] = None,
                    location              : Option[Location] = None,
                    new_chat_participant  : Option[User] = None,
                    left_chat_participant : Option[User] = None,
                    new_chat_title        : Option[String] = None,
                    new_chat_photo        : Option[Array[PhotoSize]] = None,
                    delete_chat_photo     : Option[Boolean] = None,
                    group_chat_created    : Option[Boolean] = None
                    )

/**
 * Update
 *
 * This object represents an incoming update.
 * Field 	Type 	Description
 * update_id 	Integer 	The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
 * message 	Message 	Optional. New incoming message of any kind — text, photo, sticker, etc.
 */
case class Update(
                   update_id : Int,
                   message   : Option[Message] = None
                   )

case class ReplyKeyboardHide()
case class ForceReply()

class TelegramBot(token: String) {

  private val apiBaseURL = "https://api.telegram.org/bot" + token + "/"

  protected def getJSON(action: String, options : (String, Any)*): JValue = {

    // Skip Nones and stringify the rest
    val p = options filter {
      case (_, None) => false
      case _ => true
    } map {
      case (a, Some(b)) => (a, b.toString)
      case (a, b) => (a, b.toString)
    }

    val query = Http(apiBaseURL + action).params(p).timeout(10000, 10000)

    println("Query: " + query.toString)

      val response = query.asString

      parseOpt(response.body) match {
        case Some(json) => (json \ "result")
        case None => throw new Exception("Invalid reponse:\n" + response.body)
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
                     action: String): Unit = {
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
}

trait Polling {
  this: TelegramBot =>
  /**
   * getUpdates
   *
   * Use this method to receive incoming updates using long polling (wiki). An Array of Update objects is returned.
   * Parameters 	Type 	Required 	Description
   * offset 	Integer 	Optional 	Identifier of the first update to be returned. Must be greater by one than the highest among the identifiers of previously received updates. By default, updates starting with the earliest unconfirmed update are returned. An update is considered confirmed as soon as getUpdates is called with an offset higher than its update_id.
   * limit 	Integer 	Optional 	Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
   * timeout 	Integer 	Optional 	Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
   *   Notes
   *     1. This method will not work if an outgoing webhook is set up.
   *     2. In order to avoid getting duplicate updates, recalculate offset after each server response.
   */
  def getUpdates(offset: Option[Int] = None,
                 limit: Option[Int] = None,
                 timeout: Option[Int] = None): Array[Update] = {

    println("Offset: " + offset.getOrElse("None"))

    getAs[Array[Update]]("getUpdates",
      "offset"  -> offset,
      "limit"   -> limit,
      "timeout" -> timeout)
  }
}


trait Webhooks {
  this: TelegramBot =>
  /**
   * setWebhook
   *
   * Use this method to specify a url and receive incoming updates via an outgoing webhook. Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized Update. In case of an unsuccessful request, we will give up after a reasonable amount of attempts.
   * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. www.example.com/<token>. Since nobody else knows your bot‘s token, you can be pretty sure it’s us.
   * Parameters 	Type 	Required 	Description
   * url 	String 	Optional 	HTTPS url to send updates to. Use an empty string to remove webhook integration
   *   Notes
   *     1. You will not be able to receive updates using getUpdates for as long as an outgoing webhook is set up.
   *     2. We currently do not support self-signed certificates.
   *     3. Ports currently supported for Webhooks: 443, 80, 88, 8443.
   */
  def setWebhook(url: Option[String]): Unit = {
    getJSON("setWebhook",
      "url" -> url)
  }
}

abstract class SimpleBot(token: String) extends TelegramBot(token) with Polling with Runnable {

  lazy val botName = {
    val user = getMe
    user.username.get
  }

  def handleUpdate(update: Update): Unit

  private var polling = true
  val pollingCycle = 5000

  override def run(): Unit = {

    println("Running: " + botName)

    var updatesOffset = 0

    while (polling) {
      for (u <- getUpdates(offset = Some(updatesOffset))) {
        handleUpdate(u)
        updatesOffset = updatesOffset max (u.update_id + 1)
      }

      Thread.sleep(pollingCycle)
    }
  }
  //def start(): Unit = (new Thread(this)).start()
  def stop(): Unit = (polling = false)
}

trait Commands {
  this : TelegramBot =>

  private val commands = mutable.HashMap[String, (Int, Seq[String]) => Unit]()
  val cmdPrefix = "/"

  def handleUpdate(update: Update): Unit = {
    for {
      msg <- update.message
      text <- msg.text
    } /* do */ {

      println("Message: " + text)

      // TODO: Allow parameters with spaces e.g. /echo "Hello World"
      val tokens = text split " "
      tokens match {
        case Array(rawCmd, args @ _*) if rawCmd startsWith cmdPrefix =>
          val cmd = (rawCmd stripPrefix cmdPrefix).toLowerCase
          if (commands contains cmd)
            commands(cmd)(msg.chat.id, args)

        case _ => /* Ignore */

      }
    }
  }

  def replyTo(chat_id: Int)(text: String): Option[Message] = {
    sendMessage(chat_id, text)
  }

  def on(command: String)(action: (Int, Seq[String]) => Unit): Unit = {
    commands += (command -> action)
  }
}

object FlunkeyBot extends SimpleBot("105458118:AAGFT0D0h0jfoc9g6kPA-PkGV1WNT1blHYw") with Commands {
  on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }
  on("echo") { (sender, args) =>
    replyTo(sender) {
      args mkString " "
    }
  }
}

object Main {
  def main (args: Array[String]): Unit = {
    FlunkeyBot.run()
  }
}
