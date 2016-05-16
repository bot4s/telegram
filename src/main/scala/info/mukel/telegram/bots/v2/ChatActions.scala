package info.mukel.telegram.bots.v2

import info.mukel.telegram.bots.v2.methods.ChatAction._
import info.mukel.telegram.bots.v2.methods.{ChatAction, SendChatAction}
import info.mukel.telegram.bots.v2.model.Message

/**
  * Provides handy chat actions shortcuts
  */
trait ChatActions {
  this : TelegramBot =>
  def typing(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.Typing))
  def uploadPhoto(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadPhoto))
  def recordVideo(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.RecordVideo))
  def uploadVideo(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadVideo))
  def recordAudio(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.RecordAudio))
  def uploadAudio(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadAudio))
  def uploadDocument(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.UploadDocument))
  def findLocation(implicit message: Message) = api.request(SendChatAction(message.sender, ChatAction.FindLocation))
}
