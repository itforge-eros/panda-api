package models.inputs

import java.util.UUID

import models.enums.SpaceCategory

case class CreateSpaceInput(departmentId: UUID,
                            name: String,
                            fullName: String,
                            description: Option[String],
                            tags: List[String],
                            category: SpaceCategory,
                            capacity: Option[Int],
                            isAvailable: Boolean)
