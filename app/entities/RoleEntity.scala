package entities

import java.time.Instant
import java.util.UUID

case class RoleEntity(id: UUID,
                      name: String,
                      description: Option[String],
                      permissions: List[String],
                      createdAt: Instant,
                      updatedAt: Instant,
                      departmentId: UUID)
