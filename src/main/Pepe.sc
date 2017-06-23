import info.mukel.telegrambot4s.marshalling.HttpMarshalling
import info.mukel.telegrambot4s.models.InlineKeyboardButton

val btn = InlineKeyboardButton.callbackGame("Hello")

HttpMarshalling.toJson[InlineKeyboardButton](btn)

