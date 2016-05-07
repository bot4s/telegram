package info.mukel.telegram.bots.v2.methods

/** Base type for multipart API requests (for file uploads)
  *
  * @tparam R Expected result type.
  *
  * Request will be serialized as multipart/form-data
  */
trait ApiRequestMultipart[R] extends ApiRequest[R]


