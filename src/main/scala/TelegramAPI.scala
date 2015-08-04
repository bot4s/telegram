
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


// TODO: Use something like Either[User, GroupChat] instead (but causes problems with the JSON extractor/deserializer)
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

// TODO
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
                    // The correct type would be Either[User, GroupChat]

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

/**
 * Type of action to broadcast.
 * Choose one, depending on what the user is about to receive:
 * typing for text messages,
 * upload_photo for photos,
 * record_video or upload_video for videos,
 * record_audio or upload_audio for audio files,
 * upload_document for general files,
 * find_location for location data.
 */
object Status extends Enumeration {
  type Status = Value
  val Typing = Value("typing")
  val UploadPhoto = Value("upload_photo")
  val RecordVideo = Value("record_video")
  val UploadVideo = Value("upload_video")
  val RecordAudio = Value("record_audio")
  val UploadAudio = Value("upload_audio")
  val UploadDocument = Value("upload_document")
  val FindLocation = Value("find_location")
}