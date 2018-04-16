package entities

import java.time.Instant
import java.util.UUID

case class SpaceEntity(id: UUID,
                       name: String,
                       fullName: String,
                       description: Option[String],
                       capacity: Option[Int],
                       isAvailable: Boolean,
                       createdAt: Instant,
                       departmentId: UUID)
