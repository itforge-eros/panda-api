package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.RequestException.{CannotCancelRequestException, RequestNotFoundException}
import definitions.exceptions.SpaceException.{CannotCreateSpaceException, SpaceNotFoundException}
import entities.RequestEntity
import models.enums.Access.RequestReadAccess
import models.enums.RequestStatus
import models.enums.RequestStatus.Pending
import models.inputs.{CancelRequestInput, CreateRequestInput}
import models.{Identity, Member, Request, Review}
import persists.{RequestPersist, ReviewPersist, SpacePersist}
import utils.Guard
import validators.RequestValidator.positiveRequestPeriod

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RequestFacade(auth: AuthorizationFacade,
                    requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist,
                    spacePersist: SpacePersist) extends BaseFacade {

  def find(id: UUID)
          (implicit identity: Identity): Try[Request] = {
    lazy val accesses = identity.accesses(maybeSpaceEntity.get.departmentId)
    lazy val maybeSpaceEntity = spacePersist.find(maybeRequestEntity.get.spaceId)
    lazy val maybeRequestEntity = requestPersist.find(id)

    validateWith(
      Guard(maybeRequestEntity.isEmpty, RequestNotFoundException),
      Guard(!isMemberOwnRequest(identity.viewer, maybeRequestEntity.get)
        && !auth.hasAccess(RequestReadAccess)(accesses), NoPermissionException)
    ) {
      requestPersist.find(id) toTry RequestNotFoundException map Request.of
    }
  }

  def reviews(id: UUID)
             (implicit identity: Identity): Try[List[Review]] = validate() {
    reviewPersist.findByRequestId(id) map Review.of
  }

  def create(input: CreateRequestInput)
            (implicit identity: Identity): Try[Request] = validateWith(
    Guard(spacePersist.find(input.spaceId).isEmpty, SpaceNotFoundException),
    positiveRequestPeriod(input.period.toRange)
  ) {
    lazy val requestEntity = RequestEntity(
      UUID.randomUUID(),
      input.body,
      input.dates,
      input.period.toRange,
      Pending.name,
      Instant.now(),
      input.spaceId,
      identity.viewer.id
    )

    requestPersist.insert(requestEntity) match {
      case true => Success(requestEntity) map Request.of
      case false => Failure(CannotCreateSpaceException)
    }
  }

  def cancel(input: CancelRequestInput)
            (implicit identity: Identity): Try[Member] = {
    lazy val maybeRequestEntity = requestPersist.find(input.requestId)

    validateWith(
      Guard(maybeRequestEntity.isEmpty, RequestNotFoundException),
      Guard(!isMemberOwnRequest(identity.viewer, maybeRequestEntity.get), NoPermissionException)
    ) {
      requestPersist.setStatus(input.requestId, RequestStatus.Cancelled.name) match {
        case true => Success(identity.viewer)
        case false => Failure(CannotCancelRequestException)
      }
    }
  }


  private def isMemberOwnRequest(member: Member, request: RequestEntity) = {
    member.id == request.clientId
  }

}
