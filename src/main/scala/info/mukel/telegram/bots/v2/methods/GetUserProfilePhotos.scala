package info.mukel.telegram.bots.v2.methods

import info.mukel.telegram.bots.v2.api.ApiRequest
import info.mukel.telegram.bots.v2.model.UserProfilePhotos

/** getUserProfilePhotos
  *
  * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
  *
  * @param userId  Integer	Unique identifier of the target user
  * @param offset  Integer	Optional	Sequential number of the first photo to be returned. By default, all photos are returned.
  * @param limit  Integer	Optional	Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
  */
case class GetUserProfilePhotos(
                               userId : Long,
                               offset : Option[Int] = None,
                               limit  : Option[Int] = None
                               ) extends ApiRequest[UserProfilePhotos]
