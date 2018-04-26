package facades

import java.net.URLEncoder
import java.security.spec.PKCS8EncodedKeySpec
import java.security.{KeyFactory, PrivateKey, Signature}
import java.time.Instant
import java.util.Base64

class ImageUploadFacade {

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

  private val bucketName = "itforge"
  private val clientId = "panda-storage@panda-198110.iam.gserviceaccount.com"
  private val privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzdgK0GTOEbXXX\ngr03RYek3cFdIbQro1uQUfSL4AqkJbS9IhipC9FB7cG29/ym7ECNhM4i7U/2YnoW\nNfEPVlfeAREW4oOokYDXcL0/VH1mzGMQ0HGS+h0uTdbH5EjctJcavZafrbD1a8K5\nYu1uQUe+Oac3ZHlYogU/sI7TEFE3O5zEStQI9hSHhG28gXVcszQvcsdWB8zbbemz\nJ23gs7rQnrMXzX0KSdGMPkH4/YvxBQ8gsLuYP0bthxxOTOTF32KV6SIqqfZjQq7X\njKJUPXxCoWytMA6QQ21+pbfBu6HVHrC5aFMN86ObB/qnXXVD8r/ghjxSx8ckTgvS\nawNZgBB1AgMBAAECggEAGROps4xc9kmMYLG4an+JAYjqMu8Ze8qPKibFlwEWw5Kw\nQ4h+7SpFUqLcQYq8D4AuUggnfNpv5MQkfrOJbIT6cL9x6aJ71sOEPzaV/0I5ZrKz\nOh+Ca1EPYE/IrjRwWQTUI4yv/j6ZXmXxNxIDjvdIxOROAS/FRBUZyilL/+LFEoRC\nMR56eesQsr5i7mO3ENhdk2MmeIVM/Lod3hZ/Ww8D8UKgk/olXphPGe6wm+gpySeM\nsE3BvsYq4Aroa3wv7YITsq7o78Jsfjn+EimUABCJrY+zC1PdasU66Ba2oK1xU8oU\nIgWruz0olzezZBmHkC+matxqyCC0dKOmVwyyoRrFCQKBgQDkXS+FZrvVPWrviTnX\nBzyTG/nc2bar+XROJZJSt9dL0CcPbrBGsjM5P8vnl48S21W//kO9dGV8aoZwUWat\niP/rUmZj38Z2VxQCd0xliP1gLvrTbtv6m91AqAQqv+5hysg1/llKkkgKa4Lt71xC\nnDZS91nqv26LuUky23BUAg5hbQKBgQDJLcnL2Do3TMC9WUXUpc5zSya72lAtgqeG\nEHgR6X+3MYoZyxKQ3/hM/xwJFhf4MRd+KHHJGkIASk2VJSTLVce717x63JPH7aR4\n3ZSiax0sfYD4DN9eQseB9TPxOhJ3HHZAeITDZWol0/e61rcCWAgqR/NLZ0PGVACi\nS1Lj5siOKQKBgHuk6MH16eV/J5MiXarLRETydMrbJkje+9YECcG3wF5QCZYVfGQK\nwClTHXf7W3/+OeOGK+88Qmam1ruhl1KGRuqG5uFUnAQfCbp8FWU0UtvtE+m+sGR0\nV8tWLUtoOA3C2bxCRt5VbaQWqgFiBDYcll09h/XOSHBmlfdvUNZrdpXRAoGAKYh0\ncssPKb+EwxKdIguhFZhcRMgLaXnh0AGyaDBGl7F4i16rVJGIl4zLYuzWwfyQlxEO\nfLAZFCpC3hmvAPOSqqsdVFgZglsDa5iDSQzIFxlJ+OvfaXxr596RfKm6ijCsER9Q\neW6EfY7HRCFz4t39wYzdI6IMEfPhtZ/LWxWYLhECgYEA1Z2rjgvs1cdxeQqmFB1G\nsv7HDXg2/Z2hXl2TtCYSC10IYJrVuG//pX+2JoWYSKS0cx7GacI8i92gEDGFxgls\nBOeIvRKmkFi0tYhrDkZzk/nxcbZdh7rM/3Xe3ooashhLQjlW7F7mKKjBIcXey1SW\n5+bX3+536dOQJALp/3I0nuI="
  private val duration = 60

}
