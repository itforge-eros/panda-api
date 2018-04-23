package entities

import java.time.Instant
import java.util.UUID

case class MemberRoleEntity(memberId: UUID,
                            roleId: UUID,
                            createdAt: Instant)
