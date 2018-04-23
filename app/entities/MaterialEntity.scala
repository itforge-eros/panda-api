package entities

import java.time.Instant
import java.util.UUID

case class MaterialEntity(id: UUID,
                          name: MultiLanguageString,
                          departmentId: UUID,
                          createdAt: Instant)
