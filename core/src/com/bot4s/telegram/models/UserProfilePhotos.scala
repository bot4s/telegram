package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import io.circe.generic.extras.Configuration

/**
 * This object represent a user's profile pictures.
 *
 * @param totalCount  Total number of profile pictures the target user has
 * @param photos      Requested profile pictures (in up to 4 sizes each)
 */
case class UserProfilePhotos(
  totalCount: Int,
  photos: Seq[Seq[PhotoSize]]
)

object UserProfilePhotos {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeDecoder: Decoder[UserProfilePhotos] = deriveDecoder
  implicit val circeEncoder: Encoder[UserProfilePhotos] = deriveConfiguredEncoder
}
