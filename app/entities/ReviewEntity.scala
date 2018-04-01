package entities

import java.time.Instant
import java.util.UUID

case class ReviewEntity(requestId: UUID,
                        reviewerId: UUID,
                        description: Option[String],
                        isApproval: Boolean,
                        createdAt: Instant)
