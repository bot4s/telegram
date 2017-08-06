package info.mukel.telegrambot4s.methods

/** Base type for JSON-encoded API requests
  *
  * @tparam R Expected result type.
  *
  * The request will be sent as application/json
  */
trait ApiRequestJson[R] extends ApiRequest[R]
