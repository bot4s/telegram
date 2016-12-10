package info.mukel.telegrambot4s.models

/** This object represents the contents of a file to be uploaded.
  * Must be posted using multipart/form-data in the usual way that files are uploaded via the browser.
  *
  * Resending files without reuploading
  * There are two ways of sending a file (photo, sticker, audio etc.). If it's a new file, you can upload it using multipart/form-data. If the file is already on our servers, you don't need to reupload it: each file object has a file_id field, you can simply pass this file_id as a parameter instead.  *
  *     It is not possible to change the file type when resending by file_id. I.e. a video can't be sent as a photo, a photo can't be sent as a document, etc.
  *     It is not possible to resend thumbnails.
  *     Resending a photo by file_id will send all of its sizes.
  */
sealed trait InputFile

object InputFile {
  private[telegrambot4s] final case class FileId(fileId: String) extends InputFile
  private[telegrambot4s] final case class Path(path: java.nio.file.Path) extends InputFile
  private[telegrambot4s] final case class Contents(filename: String, contents: Array[Byte]) extends InputFile
  private[telegrambot4s] final case class ByteString(filename: String, contents: akka.util.ByteString) extends InputFile

  def apply(fileId: String): InputFile = FileId(fileId)
  def apply(path: java.nio.file.Path): InputFile = Path(path)
  def apply(filename: String, contents: Array[Byte]): InputFile = Contents(filename, contents)
  def apply(filename: String, contents: akka.util.ByteString): InputFile = ByteString(filename, contents)
}
