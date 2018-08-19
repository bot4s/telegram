package com.bot4s.telegram.models

/**
  * This object represents a chat photo.
  *
  * @param smallFileId  String Unique file identifier of small (160x160) chat photo. This file_id can be used only for photo download.
  * @param bigFileId    String Unique file identifier of big (640x640) chat photo. This file_id can be used only for photo download.
  */
case class ChatPhoto(
                      smallFileId : String,
                      bigFileId   : String
                    )
