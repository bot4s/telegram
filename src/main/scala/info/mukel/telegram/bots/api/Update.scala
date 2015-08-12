package info.mukel.telegram.bots.api

/**
 * Update
 *
 * This object represents an incoming update.
 *
 * @param updateId  The update‘s unique identifier. Update identifiers start from a certain positive number and increase sequentially. This ID becomes especially handy if you’re using Webhooks, since it allows you to ignore repeated updates or to restore the correct update sequence, should they get out of order.
 * @param message   Optional. New incoming message of any kind — text, photo, sticker, etc.
 */
case class Update(
                    updateId : Int,
                    message  : Option[Message] = None
                    )
