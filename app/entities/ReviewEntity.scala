package entities

import java.time.Instant
import java.util.UUID

case class ReviewEntity(id: UUID,
                        body: Option[String],
                        event: String,
                        createdAt: Instant,
                        requestId: UUID,
                        reviewerId: UUID)
