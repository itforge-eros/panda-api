package schemas.inputs

import java.util.UUID

case class CreateSpaceInput(name: String,
                            fullName: String,
                            description: Option[String],
                            capacity: Option[Int],
                            isAvailable: Boolean,
                            departmentId: UUID)
