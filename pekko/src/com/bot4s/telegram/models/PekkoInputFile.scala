package com.bot4s.telegram.models

import org.apache.pekko.util.{ ByteString => PekkoByteString }

trait PekkoInputFile extends InputFile

object PekkoInputFile {
  final case class ByteString(filename: String, contents: PekkoByteString) extends PekkoInputFile

  def apply(filename: String, contents: PekkoByteString): PekkoInputFile = ByteString(filename, contents)
}
