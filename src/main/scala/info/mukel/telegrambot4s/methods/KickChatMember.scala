package info.mukel.telegrambot4s.methods

/**
  * Use this method to kick a user from a group, a supergroup or a channel.
  * In the case of supergroups and channels, the user will not be able to return to the group on their own using invite links, etc., unless unbanned first.
  * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
  * Returns True on success.
  *
  * '''Note:'''
  *   In regular groups (non-supergroups), this method will only work if the "All Members Are Admins" setting is off in the target group.
  *   Otherwise members may only be removed by the group's creator or by the member that added them.
  *
  * @param chatId     Integer or String Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
  * @param userId     Integer Unique identifier of the target user
  * @param untilDate  Integer Optional Date when the user will be unbanned, unix time.
  *                   If user is banned for more than 366 days or less than 30 seconds from the current time they are considered to be banned forever
  */
case class KickChatMember(
                         chatId    : Long Either String,
                         userId    : Long,
                         untilDate : Option[Int] = None
                         ) extends ApiRequestJson[Boolean]
