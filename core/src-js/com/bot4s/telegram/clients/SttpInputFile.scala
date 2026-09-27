package com.bot4s.telegram.clients

import com.bot4s.telegram.models.InputFile
import sttp.client4._
import sttp.model.Part

private[clients] object SttpInputFile {
  def multipartBodyPart(key: String, inputFile: InputFile): Option[Part[BasicBodyPart]] =
    inputFile match {
      // FileId is submitted through the JSON fields, see `inputFileEncoder`.
      case InputFile.FileId(_)                    => None
      case InputFile.Contents(filename, contents) => Some(multipart(key, contents).fileName(filename))
    }
}
