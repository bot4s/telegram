package info.mukel.telegrambot4s.methods

/** Base type for API requests
  *
  * All queries to the Telegram Bot API must be served over HTTPS and need to be presented in this form: https://api.telegram.org/bot<token>/METHOD_NAME. Like this for example
  *
  * https://api.telegram.org/bot123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11/getMe
  *
  * We support GET and POST HTTP methods. We support four ways of passing parameters in Bot API requests:
  *  - URL query string
  *  - application/x-www-form-urlencoded
  *  - application/json (except for uploading files)
  *  - multipart/form-data (use to upload files)
  *
  * All methods in the Bot API are case-insensitive.
  * All queries must be made using UTF-8.
  *
  * @tparam R Expected result type.
  */
trait ApiRequest[R]
