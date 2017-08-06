package info.mukel.telegrambot4s.methods

import info.mukel.telegrambot4s.models.ResponseParameters

/** Telegram Bot API Response object
  *
  * The response contains a JSON object. If 'ok' equals true, the request was successful and the result of the query can be found in the 'result' field.
  * In case of an unsuccessful request, 'ok' equals false and the error is explained in the 'description'.
  * An Integer 'error_code' field is also returned, but its contents are subject to change in the future.
  *
  * @param ok           Boolean Signals if the request was successful
  * @param result       Optional R Contains the response in a type-safely way
  * @param description  Optional String A human-readable description of the result
  * @param errorCode    Optional Integer Error code
  * @tparam R           Expected result type
  */
case class ApiResponse[R](
                           ok          : Boolean,
                           result      : Option[R] = None,
                           description : Option[String] = None,
                           errorCode   : Option[Int] = None,
                           parameters  : Option[ResponseParameters] = None
                         )
