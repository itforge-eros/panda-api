package facades

import java.net.URLEncoder
import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyFactory, PrivateKey, Signature}
import java.time.Instant
import java.util.Base64

import play.api.Configuration

class ImageUploadFacade(configuration: Configuration) {

  def createSpaceUploadSignUrl(spaceId: String): String = {
    val baseUrl = s"https://storage.googleapis.com/$bucketName/panda/images/spaces/$spaceId/1.jpg"
    val expiredAt = Instant.now().getEpochSecond + duration
    val urlEncodedSignature = URLEncoder.encode(
      signString(createSignatureString(spaceId, expiredAt), getPrivateKey),
      "UTF-8"
    )

    s"$baseUrl?GoogleAccessId=$clientId&Expires=$expiredAt&Signature=$urlEncodedSignature"
  }


  private def createSignatureString(spaceId: String, expiredAt: Long): String = {
    val httpVerb = "PUT"
    val md5Digest = ""
    val contentType = "image/jpeg"
    val resourcePath = s"/$bucketName/panda/images/spaces/$spaceId/1.jpg"

    s"$httpVerb\n$md5Digest\n$contentType\n$expiredAt\n$resourcePath"
  }

  private def signString(s: String, privateKey: PrivateKey): String = {
    val privateSignature = Signature.getInstance("SHA256withRSA")
    privateSignature.initSign(privateKey)
    privateSignature.update(s.getBytes("UTF-8"))

    Base64.getEncoder.encodeToString(privateSignature.sign)
  }

  private def getPrivateKey: PrivateKey = {
    val b64 = Base64.getDecoder.decode(removePrivateKeyTag(privateKey))
    val spec = new PKCS8EncodedKeySpec(b64)

    KeyFactory.getInstance("RSA").generatePrivate(spec)
  }

  private def removePrivateKeyTag(privateKey: String): String = {
    privateKey
      .replaceAll("-----END PRIVATE KEY-----", "")
      .replaceAll("-----BEGIN PRIVATE KEY-----", "")
      .replaceAll("\n", "")
  }

  private val bucketName = configuration.get[String]("google.bucket")
  private val clientId = configuration.get[String]("google.credential.id")
  private val privateKey = configuration.get[String]("google.credential.key")
  private val duration = 60

}
