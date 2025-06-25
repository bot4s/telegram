import java.net.{ InetSocketAddress, Proxy }

import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.FutureSttpClient
import com.bot4s.telegram.future.Polling

import scala.concurrent.Future
import sttp.client3._
import sttp.client3.okhttp.OkHttpFutureBackend
import okhttp3.OkHttpClient

/**
 * Tunnel the bot through a SOCKS proxy.
 *
 * To test locally via [[https://linux.die.net/man/8/sshd sshd]] use:
 *   ssh -D 1337 -C -N you_user_name@localhost
 */
class ProxyBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] {

  override implicit val backend: SttpBackend[Future, Any] = {
    val proxy = new Proxy(Proxy.Type.SOCKS, InetSocketAddress.createUnresolved("localhost", 1337))
    val okHttpClient: OkHttpClient = new OkHttpClient.Builder()
      .proxy(proxy)
      .build()
    OkHttpFutureBackend.usingClient(okHttpClient)
  }

  override lazy val client = new FutureSttpClient(token)

  onCommand("hello") { implicit msg =>
    reply("Hi " + msg.from.fold("Mr. X")(_.firstName)).void
  }
}
