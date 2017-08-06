package info.mukel.telegrambot4s.models

/** This object represent a user's profile pictures.
  *
  * @param totalCount  Total number of profile pictures the target user has
  * @param photos      Requested profile pictures (in up to 4 sizes each)
  */
case class UserProfilePhotos(
                              totalCount : Int,
                              photos     : Seq[Seq[PhotoSize]]
                            )
