package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatAdministratorRights

/**
 *  Use this method to change the default administrator rights requested by the bot when it's added as an administrator to groups or channels.
 *  These rights will be suggested to users, but they are are free to modify the list before adding the bot.
 *  Returns True on success.
 *
 * @param rights      Optional ChatAdministratorRights. A JSON-serialized object describing new default administrator rights.
 *                    If not specified, the default administrator rights will be cleared.
 * @param forChannels Optional Boolean. Pass True to change the default administrator rights of the bot in channels.
 *                    Otherwise, the default administrator rights of the bot for groups and supergroups will be changed.
 */
case class SetMyDefaultAdministratorRights(
  rights: Option[ChatAdministratorRights] = None,
  forChannels: Option[Boolean] = None
) extends JsonRequest[Boolean]
