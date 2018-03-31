package models

import java.time.Instant
import java.util.UUID

case class Review(requestId: UUID,
                  reviewerId: UUID,
                  description: Option[String],
                  isApproval: Boolean,
                  createdAt: Instant)
