package info.mukel.telegram.bots.api

/**
 * Created by mukel on 8/6/15.
 */
// TODO: Use something like Either[User, GroupChat] instead (but causes problems with the JSON extractor/deserializer)
case class UserOrGroupChat(id : Int)
