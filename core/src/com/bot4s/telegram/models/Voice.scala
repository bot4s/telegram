package com.bot4s.telegram.models

/** This object represents a voice note.
  *
  * @param fileId       Identifier for this file
  * @param fileUniqueId Unique identifier for this file
  * @param duration     Duration of the audio in seconds as defined by sender
  * @param mimeType     Optional MIME type of the file as defined by sender
  * @param fileSize     Optional File size
  */
case class Voice(
                fileId        : String,
                fileUniqueId  : String,
                duration      : Int,
                mimeType      : Option[String] = None,
                fileSize      : Option[Int] = None
                )
