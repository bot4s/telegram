package info.mukel.telegrambot4s.methods

/** Use this method to kick a user from a group or a supergroup.
  * In the case of supergroups, the user will not be able to return to the group on their own using invite links, etc., unless unbanned first.
  * The bot must be an administrator in the group for this to work. Returns True on success.
  *
  * Note:
  *   This will method only work if the ‘All Members Are Admins’ setting is off in the target group.
  *   Otherwise members may only be removed by the group's creator or by the member that added them.
  *
  * @param chatId  Integer or String Unique identifier for the target group or username of the target supergroup (in the format @supergroupusername)
  * @param userId  Integer	Unique identifier of the target user
  */
case class KickChatMember(
                         chatId: Either[Long, String],
                         userId: Long) extends ApiRequestJson[Boolean]
