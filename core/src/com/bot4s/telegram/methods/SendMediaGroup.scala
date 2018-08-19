package com.bot4s.telegram.methods

import com.bot4s.telegram.models.Message
import com.bot4s.telegram.models._

/**
  * Use this method to send a group of photos or videos as an album.
  * On success, an array of the sent Messages is returned.
  *
  * @param chatId               Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
  * @param media                Array of InputMedia A JSON-serialized array describing photos and videos to be sent, must include 2–10 items
  * @param disableNotification  Boolean Optional Sends the messages silently. Users will receive a notification with no sound.
  * @param replyToMessageId     Integer Optional If the messages are a reply, ID of the original message
  */
case class SendMediaGroup(chatId              : ChatId,
                          media               : Array[InputMedia],
                          disableNotification : Option[Boolean] = None,
                          replyToMessageId    : Option[Int] = None) extends MultipartRequest[Array[Message]] {

  override def getFiles: List[(String, InputFile)] = {
    val attachPrefix = "attach://"
    media.toList.flatMap {
      case photo: InputMediaPhoto => photo.photo.map(photo.media.stripPrefix(attachPrefix) -> _)
      case video: InputMediaVideo => video.video.map(video.media.stripPrefix(attachPrefix) -> _)
      case audio: InputMediaAudio => audio.audio.map(audio.media.stripPrefix(attachPrefix) -> _)
      case document: InputMediaDocument => document.document.map(document.media.stripPrefix(attachPrefix) -> _)
      case animation: InputMediaAnimation => animation.animation.map(animation.media.stripPrefix(attachPrefix) -> _)
    }
  }
}