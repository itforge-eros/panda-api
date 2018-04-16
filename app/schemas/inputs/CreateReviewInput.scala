package schemas.inputs

import java.util.UUID

import models.enums.ReviewEvent

case class CreateReviewInput(requestId: UUID,
                             body: Option[String],
                             event: ReviewEvent)
