package com.bot4s.telegram.models

/** This object represents a file ready to be downloaded.
  *
  * The file can be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>.
  * It is guaranteed that the link will be valid for at least 1 hour.
  * When the link expires, a new one can be requested by calling getFile.
  * Maximum file size to download is 20 MB
  *
  * @param fileId    Identifier for this file
  * @param fileUniqueId Unique identifier for this file
  * @param fileSize  Optional File size, if known
  * @param filePath  Optional File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
  */
case class File(
                 fileId   : String,
                 fileUniqueId : String,
                 fileSize : Option[Int] = None,
                 filePath : Option[String] = None
               )