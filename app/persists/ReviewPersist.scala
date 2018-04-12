package persists

import java.util.UUID

import entities.ReviewEntity

trait ReviewPersist {

  def find(requestId: UUID, reviewerId: UUID): Option[ReviewEntity]

  def findByRequestId(requestId: UUID): List[ReviewEntity]

  def findByReviewerId(reviewerId: UUID): List[ReviewEntity]

  def insert(reviewEntity: ReviewEntity): Boolean

}
