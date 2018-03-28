package models

import java.time.Instant
import java.util.UUID

case class Approval(requestId: UUID,
                    approverId: UUID,
                    createdAt: Instant)
