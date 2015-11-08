package info.mukel.telegram.bots.api

/**
 *  As of September 18, 2015
 *
 *  Bots can now download files and media sent by users.
 *  Added getFile(https://core.telegram.org/bots/api#getFile) and File(https://core.telegram.org/bots/api#file).
 *
 * This object represents a file ready to be downloaded. The file can be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>. It is
 * guaranteed that the link will be valid for at least 1 hour. When the link expires, a new one can be requested by calling getFile.
 * Important: Maximum file size to download is 20 MB
 * Field	Type	Description
 * @param fileId	Unique identifier for this file
 * @param fileSize  Optional File size, if known
 * @param filePath	Optional File path. Use https://api.telegram.org/file/bot<token>/<file_path> to get the file.
 */
class File(val fileId: String, val fileSize: Option[Int], val filePath: Option[String])
