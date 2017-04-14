package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.{ChatAction, SendChatAction}
import info.mukel.telegrambot4s.models.Message

/** Provides handy chat actions shortcuts
  */
trait ChatActions {
  _ : BotBase =>
  def typing(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.Typing))
  def uploadingPhoto(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadPhoto))
  def recordingVideo(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.RecordVideo))
  def uploadingVideo(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadVideo))
  def recordingAudio(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.RecordAudio))
  def uploadingAudio(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadAudio))
  def uploadingDocument(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.UploadDocument))
  def findingLocation(implicit msg: Message) = request(SendChatAction(msg.source, ChatAction.FindLocation))
}
