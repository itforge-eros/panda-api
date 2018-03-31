package persists

import java.util.UUID

import models.Review

trait ReviewPersist {

  def findByRequestId(requestId: UUID): List[Review]

  def findByReviewerId(reviewerId: UUID): List[Review]

}
