package com.bot4s.telegram.api

import com.bot4s.telegram.methods.{ ChatAction, SendChatAction }
import com.bot4s.telegram.models.Message
import io.circe.Decoder._

/**
 * Provides handy chat actions shortcuts.
 */
trait ChatActions[F[_]] {
  this: BotBase[F] =>
  def typing(implicit msg: Message): F[Boolean] = {
    val x = request(SendChatAction(msg.source, ChatAction.Typing))
    x
  }

  def uploadingPhoto(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadPhoto))

  def recordingVideo(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.RecordVideo))

  def uploadingVideo(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadVideo))

  def recordingAudio(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.RecordAudio))

  def uploadingAudio(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadAudio))

  def uploadingDocument(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadDocument))

  def findingLocation(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.FindLocation))
}
