import java.net.URLEncoder

import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ HttpRequest, Uri }
import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshal
import com.bot4s.telegram.api.Webhook
import com.bot4s.telegram.methods._
import com.bot4s.telegram.models.Message

import scala.concurrent.Future
import com.bot4s.telegram.models.InputFile
import java.io.File
import org.apache.pekko.http.scaladsl.HttpsConnectionContext
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.SSLContext
import org.apache.pekko.http.scaladsl.ConnectionContext
import java.security.SecureRandom

/**
 * Webhook-backed JS calculator.
 * This version uses an https akka server to serve its own certificates
 * To generate certificates, go in the `examples/resources/ssl` directory and run the following commands:
 * - keytool -genkey -keyalg RSA -alias mts -keystore mts.jks -storepass changeit -validity 360 -keysize 2048
 * - keytool -importkeystore -srckeystore mts.jks -destkeystore mts.p12 -srcstoretype jks -deststoretype pkcs12
 * - openssl pkcs12 -in mts.p12 -out mts.pem -passin pass:changeit
 * - edit mts.pem and keep only the public part (keep BEGIN and END section)
 *
 * After starting your bot, you can check the certificate status
 * https://api.telegram.org/botXXXXX/getWebhookInfo
 *
 * Note that it can take up to a few dozens seconds for the webhook to receive the first events
 *
 * Replace XXXX with your bot's key
 */
class WebhookSSLBot(token: String) extends PekkoExampleBot(token) with Webhook {

  val port       = 8080
  val webhookUrl = "https://domain.extension:8443"

  val baseUrl = "http://api.mathjs.org/v1/?expr="

  /*

   */
  override def certificate: Option[InputFile] = Some(
    InputFile(new File(getClass().getClassLoader().getResource("ssl/mts.pem").toURI()).toPath)
  )

  override val httpsContext: Option[HttpsConnectionContext] = Some({
    val password = "changeit".toCharArray()

    val ks       = KeyStore.getInstance("PKCS12")
    val keystore = getClass().getClassLoader().getResourceAsStream("ssl/mts.p12")

    require(keystore != null, " - Keystore required!")
    ks.load(keystore, password)

    val keyManagerFactory: KeyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(ks, password)

    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    tmf.init(ks)

    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, tmf.getTrustManagers, new SecureRandom)
    val https: HttpsConnectionContext = ConnectionContext.httpsServer(sslContext)

    https
  })

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text.fold(Future.successful(())) { text =>
      val url = baseUrl + URLEncoder.encode(text, "UTF-8")
      for {
        res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if res.status.isSuccess()
        result <- Unmarshal(res).to[String]
        _      <- request(SendMessage(msg.source, result))
      } yield ()
    }
}
