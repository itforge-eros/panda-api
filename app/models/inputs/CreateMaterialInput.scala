package models.inputs

import java.util.UUID

case class CreateMaterialInput(departmentId: UUID,
                               name: MultiLanguageStringInput)
