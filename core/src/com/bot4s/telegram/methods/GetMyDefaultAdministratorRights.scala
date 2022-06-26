package com.bot4s.telegram.methods

import com.bot4s.telegram.models.ChatAdministratorRights

/**
 * Use this method to get the current default administrator rights of the bot. Returns ChatAdministratorRights on success.
 *
 * @param forChannels Optional Boolean. Pass True to change the default administrator rights of the bot in channels.
 *                    Otherwise, the default administrator rights of the bot for groups and supergroups will be changed.
 */
case class GetMyDefaultAdministratorRights(
  forChannels: Option[Boolean] = None
) extends JsonRequest[ChatAdministratorRights]
