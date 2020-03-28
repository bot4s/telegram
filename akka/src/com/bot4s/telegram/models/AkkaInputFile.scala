package com.bot4s.telegram.models

trait AkkaInputFile extends InputFile

object AkkaInputFile {
  final case class ByteString(filename: String, contents: akka.util.ByteString)
      extends AkkaInputFile

  def apply(filename: String, contents: akka.util.ByteString): AkkaInputFile =
    ByteString(filename, contents)
}
