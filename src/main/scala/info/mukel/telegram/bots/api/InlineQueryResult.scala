package info.mukel.telegram.bots.api

/**
  * InlineQueryResult
  *
  * This object represents one result of an inline query. Telegram clients currently support results of the following 5 types:
  *   InlineQueryResultArticle
  *   InlineQueryResultPhoto
  *   InlineQueryResultGif
  *   InlineQueryResultMpeg4Gif
  *   InlineQueryResultVideo
  */
trait InlineQueryResult

/**
  * InlineQueryResultArticle
  * Represents a link to an article or web page.
  *
  * @param type	String	Type of the result, must be article
  * @param id	String	Unique identifier for this result, 1-64 Bytes
  * @param title	String	Title of the result
  * @param messageText	String	Text of the message to be sent
  * @param parseMode	String	Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
  * @param disableWebPagePreview	Boolean	Optional. Disables link previews for links in the sent message
  * @param url	String	Optional. URL of the result
  * @param hideUrl	Boolean	Optional. Pass True, if you don't want the URL to be shown in the message
  * @param description	String	Optional. Short description of the result
  * @param thumbUrl	String	Optional. Url of the thumbnail for the result
  * @param thumbWidth	Integer	Optional. Thumbnail width
  * @param thumbHeight	Integer	Optional. Thumbnail height
  */
case class InlineQueryResultArticle(
                            `type`                 : String,
                            id                     : String,
                            title                  : String,
                            messageText            : String,
                            parseMode              : Option[String] = None,
                            disableWebPagePreview  : Option[String] = None,
                            url                    : Option[String] = None,
                            hideUrl                : Option[Boolean] = None,
                            description            : Option[String] = None,
                            thumbUrl               : Option[String] = None,
                            thumbWidth             : Option[Int] = None,
                            thumbHeight            : Option[Int] = None
                            ) extends InlineQueryResult



/**
  * InlineQueryResultPhoto
  *
  * Represents a link to a photo. By default, this photo will be sent by the user with optional caption. Alternatively, you can provide message_text to send it instead of photo.
  *
  * @param type	String	Type of the result, must be photo
  * @param id	String	Unique identifier for this result, 1-64 bytes
  * @param photoUrl	String	A valid URL of the photo. Photo must be in jpeg format. Photo size must not exceed 5MB
  * @param photoWidth	Integer	Optional. Width of the photo
  * @param photoHeight	Integer	Optional. Height of the photo
  * @param thumbUrl	String	URL of the thumbnail for the photo
  * @param title	String	Optional. Title for the result
  * @param description	String	Optional. Short description of the result
  * @param caption	String	Optional. Caption of the photo to be sent, 0-200 characters
  * @param messageText	String	Optional. Text of a message to be sent instead of the photo, 1-512 characters
  * @param parseMode	String	Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
  * @param disableWebPagePreview	Boolean	Optional. Disables link previews for links in the sent message
  */
case class InlineQueryResultPhoto(
                                   `type`                 : String,
                                   id                     : String,
                                   photoUrl               : String,
                                   photoWidth             : Option[Int] = None,
                                   photoHeight            : Option[Int] = None,
                                   thumbUrl               : String,
                                   title                  : String,
                                   description            : Option[String] = None,
                                   caption                : Option[String] = None,
                                   messageText            : Option[String] = None,
                                   parseMode              : Option[String] = None,
                                   disableWebPagePreview  : Option[String] = None
                                   ) extends InlineQueryResult

/**
  * InlineQueryResultGif
  *
  * Represents a link to an animated GIF file. By default, this animated GIF file will be sent by the user with optional caption. Alternatively, you can provide message_text to send it instead of the animation.
  *
  * @param type                   String	Type of the result, must be gif
  * @param id                     String	Unique identifier for this result, 1-64 bytes
  * @param gifUrl                 String	A valid URL for the GIF file. File size must not exceed 1MB
  * @param gifWidth               Integer	Optional. Width of the GIF
  * @param gifHeight              Integer	Optional. Height of the GIF
  * @param thumbUrl               String	URL of the static thumbnail for the result (jpeg or gif)
  * @param title                  String	Optional. Title for the result
  * @param caption                String	Optional. Caption of the GIF file to be sent, 0-200 characters
  * @param messageText            String	Optional. Text of a message to be sent instead of the animation, 1-512 characters
  * @param parseMode              String	Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
  * @param disableWebPagePreview	Boolean	Optional. Disables link previews for links in the sent message
  */
case class InlineQueryResultGif(
                                   `type`                 : String,
                                   id                     : String,
                                   gifUrl                 : String,
                                   gifWidth               : Option[Int] = None,
                                   gifHeight              : Option[Int] = None,
                                   thumbUrl               : String,
                                   title                  : String,
                                   caption                : Option[String] = None,
                                   messageText            : Option[String] = None,
                                   parseMode              : Option[String] = None,
                                   disableWebPagePreview  : Option[String] = None
                                 ) extends InlineQueryResult

/**
  * InlineQueryResultMpeg4Gif
  *
  * Represents a link to a video animation (H.264/MPEG-4 AVC video without sound). By default, this animated MPEG-4 file will be sent by the user with optional caption. Alternatively, you can provide message_text to send it instead of the animation.
  *
  * @param type                   String	Type of the result, must be mpeg4_gif
  * @param id                     String	Unique identifier for this result, 1-64 bytes
  * @param mpeg4Url               String	A valid URL for the MP4 file. File size must not exceed 1MB
  * @param mpeg4Width             Integer	Optional. Video width
  * @param mpeg4Height            Integer	Optional. Video height
  * @param thumbUrl               String	URL of the static thumbnail (jpeg or gif) for the result
  * @param title                  String	Optional. Title for the result
  * @param caption                String	Optional. Caption of the MPEG-4 file to be sent, 0-200 characters
  * @param messageText            String	Optional. Text of a message to be sent instead of the animation, 1-512 characters
  * @param parseMode              String	Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
  * @param disableWebPagePreview  Boolean	Optional. Disables link previews for links in the sent message
  */
case class InlineQueryResultMpeg4Gif(
                                 `type`                 : String,
                                 id                     : String,
                                 mpeg4Url               : String,
                                 mpeg4Width             : Option[Int] = None,
                                 mpeg4Height            : Option[Int] = None,
                                 thumbUrl               : String,
                                 title                  : Option[String] = None,
                                 caption                : Option[String] = None,
                                 messageText            : Option[String] = None,
                                 parseMode              : Option[String] = None,
                                 disableWebPagePreview  : Option[String] = None
                               ) extends InlineQueryResult

/**
  * InlineQueryResultVideo
  *
  * Represents link to a page containing an embedded video player or a video file.
  *
  * @param type                   String	Type of the result, must be video
  * @param id                     String	Unique identifier for this result, 1-64 bytes
  * @param videoUrl               String	A valid URL for the embedded video player or video file
  * @param mimeType               String	Mime type of the content of video url, “text/html” or “video/mp4”
  * @param messageText            String	Text of the message to be sent with the video, 1-512 characters
  * @param parseMode              String	Optional. Send “Markdown”, if you want Telegram apps to show bold, italic and inline URLs in your bot's message.
  * @param disableWebPagePreview  Boolean	Optional. Disables link previews for links in the sent message
  * @param videoWidth             Integer	Optional. Video width
  * @param videoHeight            Integer	Optional. Video height
  * @param videoDuration          Integer	Optional. Video duration in seconds
  * @param thumbUrl               String	URL of the thumbnail (jpeg only) for the video
  * @param title                  String	Title for the result
  * @param description            String	Optional. Short description of the result
  */
case class InlineQueryResultVideo(
                                      `type`                 : String,
                                      id                     : String,
                                      videoUrl               : String,
                                      mimeType               : String,
                                      messageText            : Option[String] = None,
                                      parseMode              : Option[String] = None,
                                      disableWebPagePreview  : Option[String] = None,
                                      videoWidth             : Option[Int] = None,
                                      videoHeight            : Option[Int] = None,
                                      videoDuration          : Option[Int] = None,
                                      thumbUrl               : String,
                                      title                  : String,
                                      description            : Option[String] = None
                                    ) extends InlineQueryResult