package com.bot4s.telegram.models

import io.circe.Encoder
import io.circe.syntax.EncoderOps

/**
 * This object represents the contents of a file to be uploaded.
 *
 * Scala.js builds support file_id reuse and in-memory contents values in the model.
 * Filesystem paths are JVM-only.
 */
trait InputFile

object InputFile {
  final case class FileId(fileId: String)                            extends InputFile
  final case class Contents(filename: String, contents: Array[Byte]) extends InputFile

  def apply(fileId: String): InputFile                          = FileId(fileId)
  def apply(filename: String, contents: Array[Byte]): InputFile = Contents(filename, contents)

  implicit val circeEncoder: Encoder[InputFile] = Encoder.instance {
    case InputFile.FileId(fileId) => fileId.asJson
    case _                        => io.circe.Json.Null
  }
}
