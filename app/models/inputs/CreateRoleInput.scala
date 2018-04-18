package models.inputs

import java.util.UUID

case class CreateRoleInput(departmentId: UUID,
                           name: String,
                           description: Option[String],
                           permissions: List[String])
