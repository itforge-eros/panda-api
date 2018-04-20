package models.inputs

import java.util.UUID

case class UpdateRoleInput(roleId: UUID,
                           name: String,
                           description: Option[String],
                           permissions: List[String])
