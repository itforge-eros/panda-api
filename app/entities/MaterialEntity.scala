package entities

import java.util.UUID

case class MaterialEntity(id: UUID,
                          name: MultiLanguageString,
                          departmentId: UUID)
