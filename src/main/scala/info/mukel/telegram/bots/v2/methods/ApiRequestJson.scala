package info.mukel.telegram.bots.v2.methods

/** Base type for JSON-encoded API requests
  *
  * @tparam R Expected result type.
  *
  * The request will be sent as application/json
  */
trait ApiRequestJson[R] extends ApiRequest[R]
