package info.mukel.telegrambot4s.methods

/** Type of action to broadcast.
  *
  * Choose one, depending on what the user is about to receive:
  *   typing for text messages,
  *   upload_photo for photos,
  *   record_video or upload_video for videos,
  *   record_audio or upload_audio for audio files,
  *   upload_document for general files,
  *   find_location for location data,
  *   record_video_note or upload_video_note for video notes.
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
  val RecordVideoNote = Value("record_video_note")
  val UploadVideoNote = Value("upload_video_note")
}
