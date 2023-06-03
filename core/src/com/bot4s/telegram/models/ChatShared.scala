package com.bot4s.telegram.models

/**
 * This object contains information about the chat whose identifier was shared with the bot using a KeyboardButtonRequestChat button.
 *
 * @param requestId Identifier of the request
 * @param chatId  	Identifier of the shared chat. This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier. The bot may not have access to the chat and could be unable to use this identifier, unless the chat is already known to the bot by some other means.
 */
case class ChatShared(requestId: Int, chatId: Long)
