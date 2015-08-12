package info.mukel.telegram.bots.api

/**
 * UserProfilePhotos
 *
 * This object represent a user's profile pictures.
 *
 * @param totalCount  Total number of profile pictures the target user has
 * @param photos      Requested profile pictures (in up to 4 sizes each)
 */
case class UserProfilePhotos(
                               totalCount : Int,
                               photos     : Array[Array[PhotoSize]]
                               )
