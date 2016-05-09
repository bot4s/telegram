package info.mukel.telegram.bots.v2.model

import java.io.{FileInputStream, InputStream, File => JavaFile}

import akka.stream.scaladsl.Source
import akka.util.ByteString

/** InputFile
  *
  * This object represents the contents of a file to be uploaded. Must be posted using multipart/form-data in the usual way that files are uploaded via the browser.
  *
  * Resending files without reuploading
  * There are two ways of sending a file (photo, sticker, audio etc.). If it‘s a new file, you can upload it using multipart/form-data. If the file is already on our servers, you don’t need to reupload it: each file object has a file_id field, you can simply pass this file_id as a parameter instead.  *
  *     It is not possible to change the file type when resending by file_id. I.e. a video can't be sent as a photo, a photo can't be sent as a document, etc.
  *     It is not possible to resend thumbnails.
  *     Resending a photo by file_id will send all of its sizes.
  */
trait InputFile {
  val mimeType: String = "application/octet-stream"
}

object InputFile {
  case class FromFile(file: JavaFile) extends InputFile
  case class FromFileId(fileId: String) extends InputFile
  case class FromSource(
                         filename      : String,
                         contentLength : Long,
                         source        : Source[ByteString, Any]
                       ) extends InputFile

  // Not supported yet
  case class FromContents(filename: String, contents: Array[Byte]) extends InputFile
  case class FromByteString(filename: String, contents: ByteString) extends InputFile
}
