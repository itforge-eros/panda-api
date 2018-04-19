package models.inputs

import java.util.UUID

import models.enums.SpaceCategory

case class UpdateSpaceInput(spaceId: UUID,
                            name: String,
                            fullName: String,
                            description: Option[String],
                            category: SpaceCategory,
                            capacity: Option[Int],
                            isAvailable: Boolean)
