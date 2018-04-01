package entities

import java.time.Instant
import java.util.UUID

case class SpaceEntity(id: UUID,
                       name: String,
                       description: Option[String],
                       capacity: Int,
                       isAvailable: Boolean,
                       createdAt: Instant)
