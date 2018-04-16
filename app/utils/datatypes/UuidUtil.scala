package utils.datatypes

import java.nio.{BufferUnderflowException, ByteBuffer}
import java.util.UUID

import org.apache.commons.codec.binary.Base64

object UuidUtil {

  def maybeUuid(s: String): Option[UUID] = try Some(UUID.fromString(s)) catch {
    case _: IllegalArgumentException => None
  }

  def uuidToBase64(uuid: UUID): String = {
    val base64 = new Base64()
    val buffer = ByteBuffer.wrap(new Array[Byte](16))
    buffer.putLong(uuid.getMostSignificantBits)
    buffer.putLong(uuid.getLeastSignificantBits)
    base64.encodeAsString(buffer.array).dropRight(2)
  }

  def uuidFromBase64(str: String): UUID = {
    val base64 = new Base64()
    val bytes = base64.decode(str)
    val buffer = ByteBuffer.wrap(bytes)
    new UUID(buffer.getLong, buffer.getLong)
  }

  def uuidToBase62(uuid: UUID): String = {
    val buffer = ByteBuffer.wrap(new Array[Byte](16))
    buffer.putLong(uuid.getMostSignificantBits)
    buffer.putLong(uuid.getLeastSignificantBits)
    new String(base62.encode(buffer.array()))
  }

  def uuidFromBase62(str: String): UUID = {
    val bytes = BigInt(base62.decode(str.getBytes())).toByteArray
    val buffer = ByteBuffer.wrap(bytes)
    try new UUID(buffer.getLong, buffer.getLong) catch {
      case _: BufferUnderflowException => throw new IllegalArgumentException()
    }
  }


  private val base62 = Base62.createInstance()

}
