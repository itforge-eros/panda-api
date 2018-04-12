package schemas.inputs

import java.util.UUID

case class ReviewInput(requestId: UUID,
                       description: Option[String])
