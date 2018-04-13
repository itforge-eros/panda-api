package entities

import java.util.UUID

case class RoleEntity(id: UUID,
                      name: String,
                      description: Option[String],
                      groupId: UUID)
