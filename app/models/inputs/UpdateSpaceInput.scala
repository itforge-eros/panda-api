package models.inputs

import java.util.UUID

case class UpdateSpaceInput(spaceId: UUID,
                            name: String,
                            fullName: String,
                            description: Option[String],
                            tags: List[String],
                            capacity: Option[Int],
                            isAvailable: Boolean)
