package info.mukel.telegrambot4s.models

/** InlineQueryResult
  *
  * This object represents one result of an inline query. Telegram clients currently support results of the following 19 types:
  *   InlineQueryResultCachedAudio
  *   InlineQueryResultCachedDocument
  *   InlineQueryResultCachedGif
  *   InlineQueryResultCachedMpeg4Gif
  *   InlineQueryResultCachedPhoto
  *   InlineQueryResultCachedSticker
  *   InlineQueryResultCachedVideo
  *   InlineQueryResultCachedVoice
  *   InlineQueryResultArticle
  *   InlineQueryResultAudio
  *   InlineQueryResultContact
  *   InlineQueryResultDocument
  *   InlineQueryResultGif
  *   InlineQueryResultLocation
  *   InlineQueryResultMpeg4Gif
  *   InlineQueryResultPhoto
  *   InlineQueryResultVenue
  *   InlineQueryResultVideo
  *   InlineQueryResultVoice
  */
sealed trait InlineQueryResult {
  def id : String
  def `type` : String
  def replyMarkup : Option[InlineKeyboardMarkup]
}

/** InlineQueryResultArticle
  *
  * Represents a link to an article or web page.
  *
  * @param type                 String Type of the result, must be article
  * @param id                   String Unique identifier for this result, 1-64 Bytes
  * @param title                String Title of the result
  * @param inputMessageContent  InputMessageContent Content of the message to be sent
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param url                  String Optional URL of the result
  * @param hideUrl              Boolean Optional Pass True, if you don't want the URL to be shown in the message
  * @param description          String Optional Short description of the result
  * @param thumbUrl             String Optional Url of the thumbnail for the result
  * @param thumbWidth           Integer Optional Thumbnail width
  * @param thumbHeight          Integer Optional Thumbnail height
  */
case class InlineQueryResultArticle(
                                   id                  : String,
                                   title               : String,
                                   inputMessageContent : InputMessageContent,
                                   replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                   url                 : Option[String] = None,
                                   hideUrl             : Option[Boolean] = None,
                                   description         : Option[String] = None,
                                   thumbUrl            : Option[String] = None,
                                   thumbWidth          : Option[Int] = None,
                                   thumbHeight         : Option[Int] = None,
                                   `type`              : String = "article"
                                   ) extends InlineQueryResult

/** Represents a link to a photo.
  *
  * By default, this photo will be sent by the user with optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the photo.
  *
  * @param type                 String Type of the result, must be photo
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param photoUrl             String A valid URL of the photo. Photo must be in jpeg format. Photo size must not exceed 5MB
  * @param thumbUrl             String URL of the thumbnail for the photo
  * @param photoWidth           Integer Optional Width of the photo
  * @param photoHeight          Integer Optional Height of the photo
  * @param title                String Optional Title for the result
  * @param description          String Optional Short description of the result
  * @param caption              String Optional Caption of the photo to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the photo
  */
case class InlineQueryResultPhoto(
                                 id                  : String,
                                 photoUrl            : String,
                                 thumbUrl            : String,
                                 photoWidth          : Option[Int] = None,
                                 photoHeight         : Option[Int] = None,
                                 title               : Option[String] = None,
                                 description         : Option[String] = None,
                                 caption             : Option[String] = None,
                                 replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                 inputMessageContent : Option[InputMessageContent] = None,
                                 `type`              : String = "photo"
                                 ) extends InlineQueryResult

/** Represents a Game.
  * @param type           String Type of the result, must be game
  * @param id             String Unique identifier for this result, 1-64 bytes
  * @param gameShortName  String Short name of the game
  * @param replyMarkup    InlineKeyboardMarkup Optional. Inline keyboard attached to the message
  *
  * Note: This will only work in Telegram versions released after October 1, 2016. Older clients will not display any inline results if a game result is among them.
  */
case class InlineQueryResultGame(
                                  `type`        : String,
                                  id            : String,
                                  gameShortName : String,
                                  replyMarkup   : Option[InlineKeyboardMarkup] = None
                                )

/** Represents a link to an animated GIF file.
  *
  * By default, this animated GIF file will be sent by the user with optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
  *
  * @param type                 String Type of the result, must be gif
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param gifUrl               String A valid URL for the GIF file. File size must not exceed 1MB
  * @param gifWidth             Integer Optional Width of the GIF
  * @param gifHeight            Integer Optional Height of the GIF
  * @param thumbUrl             String URL of the static thumbnail for the result (jpeg or gif)
  * @param title                String Optional Title for the result
  * @param caption              String Optional Caption of the GIF file to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the GIF animation
  */
case class InlineQueryResultGif(
                               id                  : String,
                               gifUrl              : String,
                               gifWidth            : Option[Int] = None,
                               gifHeight           : Option[Int] = None,
                               thumbUrl            : String,
                               title               : Option[String] = None,
                               caption             : Option[String] = None,
                               replyMarkup         : Option[InlineKeyboardMarkup] = None,
                               inputMessageContent : Option[InputMessageContent] = None,
                               `type`              : String = "gif"
                               ) extends InlineQueryResult

/** Represents a link to a video animation (H.264/MPEG-4 AVC video without sound).
  *
  * By default, this animated MPEG-4 file will be sent by the user with optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
  *
  * @param type                 String Type of the result, must be mpeg4_gif
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param mpeg4Url             String A valid URL for the MP4 file. File size must not exceed 1MB
  * @param mpeg4Width           Integer Optional Video width
  * @param mpeg4Height          Integer Optional Video height
  * @param thumbUrl             String URL of the static thumbnail (jpeg or gif) for the result
  * @param title                String Optional Title for the result
  * @param caption              String Optional Caption of the MPEG-4 file to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the video animation
  */
case class InlineQueryResultMpeg4Gif(
                                 id                  : String,
                                 mpeg4Url            : String,
                                 mpeg4Width          : Option[Int] = None,
                                 mpeg4Height         : Option[Int] = None,
                                 thumbUrl            : String,
                                 title               : Option[String] = None,
                                 caption             : Option[String] = None,
                                 replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                 inputMessageContent : Option[InputMessageContent] = None,
                                 `type`              : String = "mpeg4_gif"
                               ) extends InlineQueryResult

/** InlineQueryResultVideo
  *
  * Represents a link to a page containing an embedded video player or a video file.
  * By default, this video file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the video.
  *
  * @param type                 String Type of the result, must be video
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param videoUrl             String A valid URL for the embedded video player or video file
  * @param mimeType             String Mime type of the content of video url, "text/html" or "video/mp4"
  * @param thumbUrl             String URL of the thumbnail (jpeg only) for the video
  * @param title                String Title for the result
  * @param caption              String Optional Caption of the video to be sent, 0-200 characters
  * @param videoWidth           Integer Optional Video width
  * @param videoHeight          Integer Optional Video height
  * @param videoDuration        Integer Optional Video duration in seconds
  * @param description          String Optional Short description of the result
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the video
  */
case class InlineQueryResultVideo(
                                 id                  : String,
                                 videoUrl            : String,
                                 mimeType            : String,
                                 thumbUrl            : String,
                                 title               : Option[String] = None,
                                 caption             : Option[String] = None,
                                 videoWidth          : Option[Int] = None,
                                 videoHeight         : Option[Int] = None,
                                 videoDuration       : Option[Int] = None,
                                 description         : Option[String] = None,
                                 replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                 inputMessageContent : Option[InputMessageContent] = None,
                                 `type`              : String = "video"
                                 ) extends InlineQueryResult

/** InlineQueryResultAudio
  *
  * Represents a link to an mp3 audio file. By default, this audio file will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the audio.
  *
  * @param type                 String Type of the result, must be audio
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param audioUrl             String A valid URL for the audio file
  * @param title                String Title
  * @param caption             String Optional. Caption, 0-200 characters
  * @param performer            String Optional Performer
  * @param audioDuration        Integer Optional Audio duration in seconds
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the audio
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultAudio(
                                   id                  : String,
                                   audioUrl            : String,
                                   title               : Option[String] = None,
                                   caption             : Option[String] = None,
                                   performer           : Option[String] = None,
                                   audioDuration       : Option[Int] = None,
                                   replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                   inputMessageContent : Option[InputMessageContent] = None,
                                   `type`              : String = "audio"
                                 ) extends InlineQueryResult

/** InlineQueryResultVoice
  *
  * Represents a link to a voice recording in an .ogg container encoded with OPUS.
  * By default, this voice recording will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the the voice message.
  *
  * @param type                 String Type of the result, must be voice
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param voiceUrl             String A valid URL for the voice recording
  * @param title                String Recording title
  * @param caption             String Optional. Caption, 0-200 characters
  * @param voiceDuration        Integer Optional Recording duration in seconds
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent InputMessageContent Optional Content of the message to be sent instead of the voice recording
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultVoice(
                                   id                  : String,
                                   voiceUrl            : String,
                                   title               : Option[String] = None,
                                   caption             : Option[String] = None,
                                   voiceDuration       : Option[Int] = None,
                                   replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                   inputMessageContent : Option[InputMessageContent] = None,
                                   `type`              : String = "voice"
                                 ) extends InlineQueryResult

/** InlineQueryResultDocument
  *
  * Represents a link to a file. By default, this file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the file.
  * Currently, only .PDF and .ZIP files can be sent using this method.
  *
  * @param type String Type of the result, must be document
  * @param id String Unique identifier for this result, 1-64 bytes
  * @param title String Title for the result
  * @param caption String Optional Caption of the document to be sent, 0-200 characters
  * @param documentUrl String A valid URL for the file
  * @param mimeType String Mime type of the content of the file, either "application/pdf" or "application/zip"
  * @param description String Optional Short description of the result
  * @param replyMarkup InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent InputMessageContent Optional Content of the message to be sent instead of the file
  * @param thumbUrl String Optional URL of the thumbnail (jpeg only) for the file
  * @param thumbWidth Integer Optional Thumbnail width
  * @param thumbHeight Integer Optional Thumbnail height
  */
case class InlineQueryResultDocument(
                                     id                  : String,
                                     title               : String,
                                     caption             : Option[String] = None,
                                     documentUrl         : Option[String] = None,
                                     mimeType            : String, // either "application/pdf" or "application/zip"
                                     description         : Option[String] = None,
                                     replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                     inputMessageContent : Option[InputMessageContent] = None,
                                     thumbUrl            : Option[String] = None,
                                     thumbWidth          : Option[Int] = None,
                                     thumbHeight         : Option[Int] = None,
                                     `type`              : String = "document"
                                   ) extends InlineQueryResult

/** InlineQueryResultLocation
  *
  * Represents a location on a map. By default, the location will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the location.
  *
  * @param type                 String Type of the result, must be location
  * @param id                   String Unique identifier for this result, 1-64 Bytes
  * @param latitude             Float number Location latitude in degrees
  * @param longitude            Float number Location longitude in degrees
  * @param title                String Location title
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the location
  * @param thumbUrl             String Optional Url of the thumbnail for the result
  * @param thumbWidth           Integer Optional Thumbnail width
  * @param thumbHeight          Integer Optional Thumbnail height
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultLocation(
                                    id                  : String,
                                    latitude            : Double,
                                    longitude           : Double,
                                    title               : String,
                                    replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                    inputMessageContent : Option[InputMessageContent] = None,
                                    thumbUrl            : Option[String] = None,
                                    thumbWidth          : Option[Int] = None,
                                    thumbHeight         : Option[Int] = None,
                                    `type`              : String = "location"
                                    ) extends InlineQueryResult

/** InlineQueryResultVenue
  *
  * Represents a venue. By default, the venue will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the venue.
  *
  * @param type                 String Type of the result, must be venue
  * @param id                   String Unique identifier for this result, 1-64 Bytes
  * @param latitude             Float Latitude of the venue location in degrees
  * @param longitude            Float Longitude of the venue location in degrees
  * @param title                String Title of the venue
  * @param address              String Address of the venue
  * @param foursquareId         String Optional Foursquare identifier of the venue if known
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the venue
  * @param thumbUrl             String Optional Url of the thumbnail for the result
  * @param thumbWidth           Integer Optional Thumbnail width
  * @param thumbHeight          Integer Optional Thumbnail height
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultVenue(
                                 id                  : String,
                                 latitude            : Double,
                                 longitude           : Double,
                                 title               : String,
                                 address             : String,
                                 foursquareId        : Option[String] = None,
                                 replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                 inputMessageContent : Option[InputMessageContent] = None,
                                 thumbUrl            : Option[String] = None,
                                 thumbWidth          : Option[Int] = None,
                                 thumbHeight         : Option[Int] = None,
                                 `type`              : String = "venue"
                                 ) extends InlineQueryResult

/** InlineQueryResultContact
  *
  * Represents a contact with a phone number. By default, this contact will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the contact.
  *
  * @param type                 String Type of the result, must be contact
  * @param id                   String Unique identifier for this result, 1-64 Bytes
  * @param phoneNumber          String Contact's phone number
  * @param firstName            String Contact's first name
  * @param lastName             String Optional Contact's last name
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the contact
  * @param thumbUrl             String Optional Url of the thumbnail for the result
  * @param thumbWidth           Integer Optional Thumbnail width
  * @param thumbHeight          Integer Optional Thumbnail height
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultContact(
                                   id                  : String,
                                   phoneNumber         : String,
                                   firstName           : String,
                                   lastName            : Option[String] = None,
                                   replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                   inputMessageContent : Option[InputMessageContent] = None,
                                   thumbUrl            : Option[String] = None,
                                   thumbWidth          : Option[Int] = None,
                                   thumbHeight         : Option[Int] = None,
                                   `type`              : String = "contact"
                                 ) extends InlineQueryResult


/** InlineQueryResultCachedPhoto
  *
  * Represents a link to a photo stored on the Telegram servers.
  * By default, this photo will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the photo.
  *
  * @param type                 String Type of the result, must be photo
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param photoFileId          String A valid file identifier of the photo
  * @param title                String Optional Title for the result
  * @param description          String Optional Short description of the result
  * @param caption              String Optional Caption of the photo to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the photo
  */
case class InlineQueryResultCachedPhoto(
                                   id                  : String,
                                   photoFileId         : String,
                                   title               : Option[String] = None,
                                   description         : Option[String] = None,
                                   caption             : Option[String] = None,
                                   replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                   inputMessageContent : Option[InputMessageContent] = None,
                                   `type`              : String = "photo"
                                 ) extends InlineQueryResult

/** InlineQueryResultCachedGif
  * Represents a link to an animated GIF file stored on the Telegram servers.
  * By default, this animated GIF file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with specified content instead of the animation.
  *
  * @param type                 String Type of the result, must be gif
  * @param id String            Unique identifier for this result, 1-64 bytes
  * @param gifFileId            String A valid file identifier for the GIF file
  * @param title                String Optional Title for the result
  * @param caption              String Optional Caption of the GIF file to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the GIF animation
  */
case class InlineQueryResultCachedGif(
                                   id                  : String,
                                   gifFileId           : String,
                                   title               : Option[String] = None,
                                   caption             : Option[String] = None,
                                   replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                   inputMessageContent : Option[InputMessageContent] = None,
                                   `type`              : String = "gif"
                                 ) extends InlineQueryResult

/** InlineQueryResultCachedMpeg4Gif
  * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound) stored on the Telegram servers.
  * By default, this animated MPEG-4 file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the animation.
  *
  * @param type                 String Type of the result, must be mpeg4_gif
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param mpeg4FileId          String A valid file identifier for the MP4 file
  * @param title                String Optional Title for the result
  * @param caption              String Optional Caption of the MPEG-4 file to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the video animation
  */
case class InlineQueryResultCachedMpeg4Gif(
                                       id                  : String,
                                       mpeg4FileId         : String,
                                       title               : Option[String] = None,
                                       caption             : Option[String] = None,
                                       replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                       inputMessageContent : Option[InputMessageContent] = None,
                                       `type`              : String = "mpeg4_gif"
                                     ) extends InlineQueryResult

/** InlineQueryResultCachedSticker
  *
  * Represents a link to a sticker stored on the Telegram servers.
  * By default, this sticker will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the sticker.
  *
  * @param type String Type of the result, must be sticker
  * @param id String Unique identifier for this result, 1-64 bytes
  * @param stickerFileId String A valid file identifier of the sticker
  * @param replyMarkup InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent InputMessageContent Optional Content of the message to be sent instead of the sticker
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultCachedSticker(
                                           id                  : String,
                                           stickerFileId       : String,
                                           replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                           inputMessageContent : Option[InputMessageContent] = None,
                                           `type`              : String = "sticker"
                                         ) extends InlineQueryResult

/** InlineQueryResultCachedDocument
  *
  * Represents a link to a file stored on the Telegram servers.
  * By default, this file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the file.
  * Currently, only pdf-files and zip archives can be sent using this method.
  *
  * @param type                 String Type of the result, must be document
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param title                String Title for the result
  * @param documentFileId       String A valid file identifier for the file
  * @param description          String Optional Short description of the result
  * @param caption              String Optional Caption of the document to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the file
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultCachedDocument(
                                         id                  : String,
                                         title               : String,
                                         documentFileId      : String,
                                         description         : Option[String] = None,
                                         caption             : Option[String] = None,
                                         replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                         inputMessageContent : Option[InputMessageContent] = None,
                                         `type`              : String = "document"
                                       ) extends InlineQueryResult

/** InlineQueryResultCachedVideo
  *
  * Represents a link to a video file stored on the Telegram servers.
  * By default, this video file will be sent by the user with an optional caption.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the video.
  *
  * @param type                 String Type of the result, must be video
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param videoFileId          String A valid file identifier for the video file
  * @param title                String Title for the result
  * @param description          String Optional Short description of the result
  * @param caption              String Optional Caption of the video to be sent, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the video
  */
case class InlineQueryResultCachedVideo(
                                       id                  : String,
                                       videoFileId         : String,
                                       title               : String,
                                       description         : Option[String] = None,
                                       caption             : Option[String] = None,
                                       replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                       inputMessageContent : Option[InputMessageContent] = None,
                                       `type`              : String = "video"
                                      ) extends InlineQueryResult

/** InlineQueryResultCachedVoice
  *
  * Represents a link to a voice message stored on the Telegram servers.
  * By default, this voice message will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the voice message.
  *
  * @param type                 String Type of the result, must be voice
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param voiceFileId          String A valid file identifier for the voice message
  * @param title                String Voice message title
  * @param caption              String Optional. Caption, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the voice message
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultCachedVoice(
                                         id                  : String,
                                         voiceFileId         : String,
                                         title               : String,
                                         caption             : Option[String] = None,
                                         replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                         inputMessageContent : Option[InputMessageContent] = None,
                                         `type`              : String = "voice"
                                       ) extends InlineQueryResult

/** InlineQueryResultCachedAudio
  *
  * Represents a link to an mp3 audio file stored on the Telegram servers.
  * By default, this audio file will be sent by the user.
  * Alternatively, you can use input_message_content to send a message with the specified content instead of the audio.
  *
  * @param type                 String Type of the result, must be audio
  * @param id                   String Unique identifier for this result, 1-64 bytes
  * @param audioFileId          String A valid file identifier for the audio file
  * @param caption              String Optional. Caption, 0-200 characters
  * @param replyMarkup          InlineKeyboardMarkup Optional An Inline keyboard attached to the message
  * @param inputMessageContent  InputMessageContent Optional Content of the message to be sent instead of the audio
  *
  * Note: This will only work in Telegram versions released after 9 April, 2016. Older clients will ignore them.
  */
case class InlineQueryResultCachedAudio(
                                         id                  : String,
                                         audioFileId         : String,
                                         caption             : Option[String] = None,
                                         replyMarkup         : Option[InlineKeyboardMarkup] = None,
                                         inputMessageContent : Option[InputMessageContent] = None,
                                         `type`              : String = "audio"
                                       ) extends InlineQueryResult
