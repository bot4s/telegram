package com.bot4s.telegram.methods

import com.bot4s.telegram.models.UserProfilePhotos
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder

/**
 * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 *
 * @param userId  Long Unique identifier of the target user
 * @param offset  Integer Optional Sequential number of the first photo to be returned. By default, all photos are returned.
 * @param limit   Integer Optional Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
 */
case class GetUserProfilePhotos(
  userId: Long,
  offset: Option[Int] = None,
  limit: Option[Int] = None
) extends JsonRequest {
  type Response = UserProfilePhotos
}

object GetUserProfilePhotos {
  implicit val customConfig: Configuration                 = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[GetUserProfilePhotos] = deriveConfiguredEncoder
}
