package utils.datatypes

import java.util.UUID

object UuidUtil {

  def maybeUuid(s: String): Option[UUID] = try Some(UUID.fromString(s)) catch {
    case _: IllegalArgumentException => None
  }

}
