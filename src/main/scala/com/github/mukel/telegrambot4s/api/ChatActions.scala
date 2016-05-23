package com.github.mukel.telegrambot4s.api

import com.github.mukel.telegrambot4s.methods.{ChatAction, SendChatAction}
import com.github.mukel.telegrambot4s.models.Message

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
