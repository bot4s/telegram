package info.mukel.telegram.bots

import info.mukel.telegram.bots.Policy._

import scala.collection.mutable

sealed trait Policy

object Policy {
  case object Accept extends Policy
  case object Decline extends Policy
}

/**
  * Filter the channels to which the bot could/should not respond
  * Two policies are available:
  *
  * - Accept: respond to any channels, except the ones that are blacklisted
  * - Decline: do not respond to any channels, except the ones that are whitelisted
  */
object AuthorizationManager {

  private val authorizations =  mutable.Set[Int]()
  private var policy : Policy = Accept

  /**
    * Finds if a channel is authorized or not
    * @param channel
    *   The channel to look the authorization for.
    * @return
    *   Whether or not the bot can respond to this channel
    */
  def isAuthorized(channel: Int) : Boolean = {
    val authorization = authorizations.contains(channel)
    policy match {
      case Accept => !authorization
      case Decline => authorization
    }
  }

  /**
    * Add a channel to the whitelisted/blacklisted
    * @param channel
    *   The channel to add
    */
  def add(channel: Int) = authorizations.add(channel)

  /**
    * Remove a channel to the whitelisted/blacklisted
    * @param channel
    *   The channel to add
    */
  def remove(channel: Int) = authorizations.remove(channel)

  /**
    * Set the Manager policy
    * @param policy
    *   The new authorization policy
    */
  def setPolicy(policy: Policy) = this.policy = policy

}

