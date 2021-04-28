package com.bot4s.telegram.models

/**
 * This object represents a chat photo.
 *
 * @param smallFileId        String File identifier of small (160x160) chat photo. This file_id can be used only for photo download.
 * @param smallFileUniqueId  String File unique identifier of small chat photo.
 * @param bigFileId          String File identifier of big (640x640) chat photo. This file_id can be used only for photo download.
 * @param bigFileUniqueId    String File unique identifier of big chat photo.
 */
case class ChatPhoto(
  smallFileId: String,
  smallFileUniqueId: String,
  bigFileId: String,
  bigFileUniqueId: String
)
