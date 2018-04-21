package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.RequestException.{RequestAlreadyClosedException, RequestNotFoundException}
import definitions.exceptions.ReviewException.{CannotCreateReviewException, ReviewNotFoundException}
import definitions.exceptions.SpaceException.SpaceNotFoundException
import entities.{RequestEntity, ReservationEntity, ReviewEntity}
import models.enums.RequestStatus.{Completed, Failed, Pending}
import models.enums.ReviewEvent
import models.inputs.CreateReviewInput
import models.{Identity, Member, Review}
import persists._
import utils.Guard

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class ReviewFacade(auth: AuthorizationFacade,
                   reviewPersist: ReviewPersist,
                   requestPersist: RequestPersist,
                   reservationPersist: ReservationPersist,
                   spacePersist: SpacePersist) extends BaseFacade {

  def find(id: UUID): Try[Review] = validateWith() {
    reviewPersist.find(id) toTry ReviewNotFoundException map Review.of
  }

  def create(input: CreateReviewInput)
            (implicit identity: Identity): Try[Review] = {
    lazy val accesses = auth.accesses(identity.viewer.id, maybeSpace.get.departmentId)
    lazy val maybeSpace = requestPersist.find(input.requestId)
      .toTry(RequestNotFoundException)
      .map(_.spaceId)
      .flatMap(spacePersist.find(_).toTry(SpaceNotFoundException))
    lazy val reviewEntity = ReviewEntity(
      UUID.randomUUID(),
      input.body,
      input.event.name,
      Instant.now(),
      input.requestId,
      identity.viewer.id
    )

    validateWith(
      Guard(maybeSpace.isFailure, maybeSpace.failed.get),
      Guard(!auth.canCreateReview(accesses.get), NoPermissionException)
    ) {
      findRequest(input.requestId) flatMap { requestEntity =>
        requestEntity.status == Pending.name match {
          case true => createReview(reviewEntity) map Review.of
          case false => Failure(RequestAlreadyClosedException)
        }
      }
    }
  }


  private def updateRequestStatus(requestId: UUID): Boolean = {
    val reviews = reviewPersist.findByRequestId(requestId)
    val approvals = reviews.filter(_.event == ReviewEvent.Approve.name)
    val rejections = reviews.filter(_.event == ReviewEvent.Reject.name)

    approvals.length >= requiredApproval match {
      case true => requestPersist.setStatus(requestId, Completed.name)
      case false => None
    }

    rejections.nonEmpty match {
      case true => requestPersist.setStatus(requestId, Failed.name)
      case false => true
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

  private def requiredApproval: Int = 2

}
