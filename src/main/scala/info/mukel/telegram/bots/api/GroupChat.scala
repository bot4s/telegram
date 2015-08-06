package info.mukel.telegram.bots.api

/**
  * GroupChat
  *
  * This object represents a group chat.
  * Field 	Type 	Description
  * id 	Integer 	Unique identifier for this group chat
  * title 	String 	Group name
  */
case class GroupChat(
                       id    : Int,
                       title : String
                       )
