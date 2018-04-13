package entities

import java.util.UUID

case class DepartmentEntity(id: UUID,
                            name: String,
                            description: Option[String])
