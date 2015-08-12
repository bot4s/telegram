package info.mukel.telegram.bots.api

/**
 * GroupChat
 *
 * This object represents a group chat.
 *
 * @param id     Unique identifier for this group chat
 * @param title  Group name
 */
case class GroupChat(
                      id    : Int,
                      title : String
                      )
