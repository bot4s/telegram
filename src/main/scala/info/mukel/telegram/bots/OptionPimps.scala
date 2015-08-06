package info.mukel.telegram.bots

/**
 * Created by mukel on 8/5/15.
 */

/**
 * Cleaner syntax for optional (Option) parameters.
 *
 * ?(true) -> Option(true) -> Some(true)
 * ?("hello") -> Option("hello") -> Some("hello")
 * ?(null) -> Option(null) -> None
 *
 * toOption allows using optional parameters in a natural way as follows:
 *
 * sendMessage(chat_id, text,
 *             disable_web_page_preview = Some(true),
 *             reply_to_message_id = Some(12345))
 *
 * Becomes:
 *
 * sendMessage(chat_id, text,
 *             disable_web_page_preview = true,
 *             reply_to_message_id = 12345)
 *
 */
object OptionPimps {
  import Option.{apply => ?}
  implicit def toOption[T](x:T) : Option[T] = Option(x)
}
