package schemas.inputs

import java.util.UUID

case class SpaceInput(name: String,
                      description: Option[String],
                      capacity: Option[Int],
                      isAvailable: Boolean,
                      groupId: UUID)
