package com.bot4s.telegram.methods

import com.bot4s.telegram.models.InputFile

/**
 * Base type for multipart API requests (for file uploads)
 *
 * @tparam R Expected result type.
 *
 * Request will be serialized as multipart/form-data
 */
trait MultipartRequest[R] extends Request[R] {
  def getFiles: List[(String, InputFile)]
}
