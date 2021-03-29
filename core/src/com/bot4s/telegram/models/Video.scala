package com.bot4s.telegram.models

/** This object represents a video file.
  *
  * @param fileId    Identifier for this file
  * @param fileUniqueId Unique file identifier
  * @param width     Video width as defined by sender
  * @param height    Video height as defined by sender
  * @param duration  Duration of the video in seconds as defined by sender
  * @param thumb     Optional Video thumbnail
  * @param mimeType  Optional Mime type of a file as defined by sender
  * @param fileSize  Optional File size
  */
case class Video(
                  fileId   : String,
                  fileUniqueId : String,
                  width    : Int,
                  height   : Int,
                  duration : Int,
                  thumb    : Option[PhotoSize] = None,
                  mimeType : Option[String] = None,
                  fileSize : Option[Int] = None
                )
