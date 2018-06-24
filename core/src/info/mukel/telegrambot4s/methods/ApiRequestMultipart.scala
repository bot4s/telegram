package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.InputFile

/** Base type for multipart API requests (for file uploads)
  *
  * @tparam R Expected result type.
  *
  * Request will be serialized as multipart/form-data
  */
trait ApiRequestMultipart[R] extends ApiRequest[R] {
  def getFiles: List[(String, InputFile)]
}
