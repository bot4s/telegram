package com.bot4s.telegram.methods

import com.bot4s.telegram.models.{ ChatId, InputFile }
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to set a new profile photo for the chat.
 * Photos can't be changed for private chats.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 *
 * '''Note:''' In regular groups (non-supergroups), this method will only work if the "All Members Are Admins" setting is off in the target group.
 *
 * @param chatId  Integer or String Unique identifier for the target chat or username of the target channel (in the format @channelusername)
 * @param photo   InputFile New chat photo, uploaded using multipart/form-data
 */
case class SetChatPhoto(
  chatId: ChatId,
  photo: InputFile
) extends MultipartRequest {
  type Response = Boolean
  override def getFiles: List[(String, InputFile)] = List("photo" -> photo)
}

object SetChatPhoto {
  implicit val customConfig: Configuration                = Configuration.default.withSnakeCaseMemberNames
  implicit val setChatPhotoEncoder: Encoder[SetChatPhoto] = deriveConfiguredEncoder[SetChatPhoto]
}
