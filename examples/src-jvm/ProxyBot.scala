import java.net.{InetSocketAddress, Proxy}

import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.ScalajHttpClient

/**
  * Tunnel the bot through a SOCKS proxy.
  *
  * To test locally via [[https://linux.die.net/man/8/sshd sshd]] use:
  *   ssh -D 1337 -C -N you_user_name@localhost
  */
class ProxyBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands {

  val proxy = new Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("localhost", 1337))

  override val client = new ScalajHttpClient(token, proxy)

  onCommand('hello) { implicit msg =>
    reply("Hi " + msg.from.fold("Mr. X")(_.firstName))
  }
}
