package entities

import java.time.Instant
import java.util.UUID

case class ProblemEntity(id: UUID,
                         title: String,
                         body: String,
                         isRead: Boolean,
                         createdAt: Instant,
                         spaceId: UUID)
