package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import entities.{RequestEntity, ReviewEntity}
import models.RequestStatus.{Approved, Pending, Rejected}
import models.{Member, Review}
import persists.{RequestPersist, ReviewPersist}
import schemas.inputs.ReviewInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class ReviewFacade(reviewPersist: ReviewPersist,
                   requestPersist: RequestPersist) extends BaseFacade {

  def create(input: ReviewInput, isApproval: Boolean)
            (implicit member: Member): Try[Review] = ValidateWith(
    Guard(reviewPersist.find(input.requestId, member.id) isDefined, ReviewAlreadyExistedException)
  ) {
    val reviewEntity = ReviewEntity(
      input.requestId,
      member.id,
      input.description,
      isApproval,
      Instant.now()
    )

    findRequest(input.requestId) flatMap { requestEntity =>
      requestEntity.status == Pending.code match {
        case true => createReview(reviewEntity) map Review.of
        case false => Failure(RequestAlreadyClosedException)
      }
    }
  }


  private def updateRequestStatus(requestId: UUID): Boolean = {
    reviewPersist.findByRequestId(requestId) partition (_.isApproval) match {
      case (approvals, _) if approvals.length >= requiredApproval =>
        requestPersist.setStatus(requestId, Approved.code)
      case (_, rejections) if rejections.nonEmpty =>
        requestPersist.setStatus(requestId, Rejected.code)
      case _ => true
    }
  }

  private def findRequest(requestId: UUID): Try[RequestEntity] = {
    requestPersist.find(requestId).toTry(RequestNotFoundException)
  }

  private def createReview(reviewEntity: ReviewEntity): Try[ReviewEntity] = {
    reviewPersist.insert(reviewEntity) && updateRequestStatus(reviewEntity.requestId) match {
      case true => Success(reviewEntity)
      case false => Failure(CannotCreateReviewException)
    }
  }

  private def requiredApproval: Int = 3

}
