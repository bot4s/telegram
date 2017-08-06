package info.mukel.telegrambot4s.models

/** This object represents one row of the high scores table for a game.
  *
  * @param position  Integer Position in high score table for the game
  * @param user      User User
  * @param score     Integer Score
  */
case class GameHighScore(
                        position : Long,
                        user     : User,
                        score    : Long
                        )
