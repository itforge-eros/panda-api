package facades

import java.util.UUID

import models.Review
import persists.ReviewPersist

import scala.util.Try

class ReviewFacade(reviewPersist: ReviewPersist) extends BaseFacade {

  def findByReviewerId(id: UUID): Try[List[Review]] = Try {
    reviewPersist.findByReviewerId(id) map Review.of
  }

}
