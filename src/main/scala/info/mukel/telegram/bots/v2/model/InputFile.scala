package info.mukel.telegram.bots.v2.model

import java.io.{FileInputStream, InputStream, File => JavaFile}

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
case class InputFile(name: String, content: Array[Byte]) {
  val mimeType: String = "application/octet-stream"
}

object InputFile {
  def apply(filePath: String): InputFile = apply(new JavaFile(filePath))

  def apply(file: JavaFile): InputFile = apply(file.getName, new FileInputStream(file))

  def apply(name: String, inputStream: InputStream): InputFile = {
    val content: Array[Byte] = Iterator.continually(inputStream.read()).takeWhile(-1 !=).map(_.toByte).toArray
    InputFile(name, content)
  }
}

object InputFileImplicits {
  implicit def toInputFile(file: JavaFile) : InputFile = InputFile(file)
}
