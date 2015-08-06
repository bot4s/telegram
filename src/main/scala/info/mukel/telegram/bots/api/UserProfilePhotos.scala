package info.mukel.telegram.bots.api

/**
  * UserProfilePhotos
  *
  * This object represent a user's profile pictures.
  * Field 	Type 	Description
  * total_count 	Integer 	Total number of profile pictures the target user has
  * photos 	Array of Array of PhotoSize 	Requested profile pictures (in up to 4 sizes each)
  */
case class UserProfilePhotos(
                               totalCount : Int,
                               photos     : Array[Array[PhotoSize]]
                               )
