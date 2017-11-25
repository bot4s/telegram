package info.mukel.telegrambot4s.models

sealed trait InputMedia

/**
  * Represents a photo to be sent.
  *
  * @param type    String Type of the result, must be photo
  * @param media   String File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
  *                pass an HTTP URL for Telegram to get a file from the Internet,
  *                or pass "attach://<file_attach_name>" to upload a new one using multipart/form-data under <file_attach_name> name.
  *                More info on Sending Files »
  * @param caption String Optional. Caption of the photo to be sent, 0-200 characters
  */
case class InputMediaPhoto(media   : String,
                           caption : Option[String] = None,
                           `type`  : String = "photo") extends InputMedia

/**
  * Represents a video to be sent.
  * @param type      String Type of the result, must be video
  * @param media     String File to send.
  *                  Pass a file_id to send a file that exists on the Telegram servers (recommended),
  *                  pass an HTTP URL for Telegram to get a file from the Internet,
  *                  or pass "attach://<file_attach_name>" to upload a new one using multipart/form-data under <file_attach_name> name.
  *                  More info on Sending Files »
  * @param caption   String Optional. Caption of the video to be sent, 0-200 characters
  * @param width     Integer Optional. Video width
  * @param height    Integer Optional. Video height
  * @param duration  Integer Optional. Video duration
  */
case class InputMediaVideo(media    : String,
                           caption  : Option[String] = None,
                           width    : Option[Int] = None,
                           height   : Option[Int] = None,
                           duration : Option[Int] = None,
                           `type`   : String = "video") extends InputMedia
