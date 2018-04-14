package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import entities.{RequestEntity, ReservationEntity, ReviewEntity}
import models.RequestStatus.{Completed, Pending, Failed}
import models.{Member, Review}
import persists.{RequestPersist, ReservationPersist, ReviewPersist}
import schemas.inputs.ReviewInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class ReviewFacade(reviewPersist: ReviewPersist,
                   requestPersist: RequestPersist,
                   reservationPersist: ReservationPersist) extends BaseFacade {

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
      requestEntity.status == Pending.name match {
        case true => createReview(reviewEntity) map Review.of
        case false => Failure(RequestAlreadyClosedException)
      }
    }
  }


  private def updateRequestStatus(requestId: UUID): Boolean = {
    reviewPersist.findByRequestId(requestId) partition (_.isApproval) match {
      case (approvals, _) if approvals.length >= requiredApproval =>
        requestPersist.setStatus(requestId, Completed.name)
      case (_, rejections) if rejections.nonEmpty =>
        requestPersist.setStatus(requestId, Failed.name)
      case _ => true
    }
  }

  private def updateReservation(requestId: UUID): Boolean = {
    requestPersist.find(requestId) map { requestEntity =>
      requestEntity.status == Completed.name match {
        case true => createReservations(requestEntity)
        case false => true
      }
    } get
  }

  private def findRequest(requestId: UUID): Try[RequestEntity] = {
    requestPersist.find(requestId).toTry(RequestNotFoundException)
  }

  private def createReview(reviewEntity: ReviewEntity): Try[ReviewEntity] = {
    reviewPersist.insert(reviewEntity) &&
      updateRequestStatus(reviewEntity.requestId) &&
      updateReservation(reviewEntity.requestId) match {
      case true => Success(reviewEntity)
      case false => Failure(CannotCreateReviewException)
    }
  }

  private def createReservations(requestEntity: RequestEntity): Boolean = {
    val reservations = requestEntity.dates map { date =>
      ReservationEntity(
        UUID.randomUUID(),
        date,
        requestEntity.period,
        isAttended = false,
        requestEntity.spaceId,
        requestEntity.clientId
      )
    }

    reservations map reservationPersist.insert forall (_ == true)
  }

  private def requiredApproval: Int = 3

}
