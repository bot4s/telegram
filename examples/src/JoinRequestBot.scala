import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.{ Commands, JoinRequests }

import scala.concurrent.Future
import com.bot4s.telegram.methods.CreateChatInviteLink
import com.bot4s.telegram.methods.CreateChatInviteLink
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.models._
import com.bot4s.telegram.methods.ApproveChatJoinRequest
import com.bot4s.telegram.methods.DeclineChatJoinRequest

/**
 * Showcases registration and join request (bot must be admin)
 *
 * @param token Bot's token.
 */
class JoinRequestBot(token: String) extends ExampleBot(token) with JoinRequests[Future] with Commands[Future] {

  var accept: Boolean = true

  onJoinRequest { joinRequest =>
    if (accept)
      request(ApproveChatJoinRequest(joinRequest.chat.chatId, joinRequest.from.id)).void
    else
      request(DeclineChatJoinRequest(joinRequest.chat.chatId, joinRequest.from.id)).void
  }

  // String commands.
  onCommand("/accept") { _ =>
    Future { accept = true }
  }

  onCommand("/deny") { _ =>
    Future { accept = false }
  }

  onCommand("/link") { implicit msg =>
    request(CreateChatInviteLink(ChatId(msg.chat.id), createsJoinRequest = Some(true))).flatMap { link =>
      reply(s"Invite link is ${link.inviteLink}").void
    }.recoverWith { case error => reply(s"Unable to create link ${error}").void }
  }

}
