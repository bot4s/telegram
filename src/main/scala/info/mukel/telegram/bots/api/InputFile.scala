package info.mukel.telegram.bots.api

import java.io.{FileInputStream, InputStream, File}

/**
 * Created by mukel on 8/16/15.
 */
trait InputFile {
  val name: String
  val mimeType: String = "application/octet-stream"
  val bytes: Array[Byte]
}

object InputFile {

  def apply(filePath: String): InputFile = apply(new File(filePath))

  def apply(file: File): InputFile = {
    apply(file.getName, new FileInputStream(file))
  }

  def apply(fileName: String, inputStream: InputStream): InputFile = new InputFile {
    val name = fileName
    val bytes = Iterator.continually(inputStream.read()) takeWhile (-1 !=) map (_.toByte) toArray
  }
}


object InputFilePimps {
  implicit def toInputFile(file: File) : InputFile = InputFile(file)
}