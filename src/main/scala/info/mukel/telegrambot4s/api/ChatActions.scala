package info.mukel.telegrambot4s.api

import info.mukel.telegrambot4s.methods.{ChatAction, SendChatAction}
import info.mukel.telegrambot4s.models.Message

/**
  * Provides handy chat actions shortcuts
  */
trait ChatActions {
  this : TelegramBot =>
  def typing(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.Typing))
  def uploadingPhoto(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadPhoto))
  def recordingVideo(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.RecordVideo))
  def uploadingVideo(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadVideo))
  def recordingAudio(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.RecordAudio))
  def uploadingAudio(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadAudio))
  def uploadingDocument(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadDocument))
  def findingLocation(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.FindLocation))
}
