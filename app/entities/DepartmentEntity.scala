package entities

import java.time.Instant
import java.util.UUID

case class DepartmentEntity(id: UUID,
                            name: String,
                            fullEnglishName: String,
                            fullThaiName: String,
                            description: Option[String],
                            createdAt: Instant,
                            updatedAt: Instant)
