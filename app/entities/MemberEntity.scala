package entities

import java.time.Instant
import java.util.UUID

case class MemberEntity(id: UUID,
                        username: String,
                        firstName: String,
                        lastName: String,
                        email: String,
                        createdAt: Instant,
                        updatedAt: Instant)
