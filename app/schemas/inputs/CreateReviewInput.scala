package schemas.inputs

import java.util.UUID

case class CreateReviewInput(requestId: UUID,
                             body: Option[String])
