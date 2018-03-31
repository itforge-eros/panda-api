package spec.mockpersists

import java.util.UUID

import persists.ReviewPersist

class MockReviewPersist extends ReviewPersist {

  override def findByRequestId(requestId: UUID) = ???

  override def findByReviewerId(reviewerId: UUID) = ???
      
}
