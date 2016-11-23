package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.{ChatAction, SendChatAction}
import info.mukel.telegrambot4s.models.Message

/** Provides handy chat actions shortcuts
  */
trait ChatActions {
  _ : BotBase =>
  def typing(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.Typing))
  def uploadingPhoto(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.UploadPhoto))
  def recordingVideo(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.RecordVideo))
  def uploadingVideo(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.UploadVideo))
  def recordingAudio(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.RecordAudio))
  def uploadingAudio(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.UploadAudio))
  def uploadingDocument(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.UploadDocument))
  def findingLocation(implicit msg: Message) = request(SendChatAction(msg.sender, ChatAction.FindLocation))
}
