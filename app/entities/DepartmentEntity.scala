package entities

import java.util.UUID

case class DepartmentEntity(id: UUID,
                            name: String,
                            fullEnglishName: String,
                            fullThaiName: String,
                            description: Option[String])
