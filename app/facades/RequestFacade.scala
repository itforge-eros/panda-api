package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.RequestException.{CannotCancelRequestException, CannotCreateRequestException, RequestNotFoundException}
import definitions.exceptions.SpaceException.CannotCreateSpaceException
import entities.RequestEntity
import models.enums.RequestStatus
import models.enums.RequestStatus.Pending
import models.inputs.{CancelRequestInput, CreateRequestInput}
import models.{Member, Request, Review}
import persists.{RequestPersist, ReviewPersist, SpacePersist}
import utils.Guard
import validators.RequestValidator.positiveRequestPeriod
import validators.SpaceValidator.spaceExist

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RequestFacade(requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist,
                    spacePersist: SpacePersist) extends BaseFacade {

  def find(id: UUID)
          (implicit viewer: Member): Try[Request] = validateWith() {
    requestPersist.find(id)
      .toTry(RequestNotFoundException)
      .filterElse(isMemberOwnRequest(viewer, _))(NoPermissionException)
      .map(Request.of)
  }

  def reviews(id: UUID)
             (implicit viewer: Member): Try[List[Review]] = validate() {
    reviewPersist.findByRequestId(id) map Review.of
  }

  def create(input: CreateRequestInput)
            (implicit viewer: Member): Try[Request] = validateWith(
    spaceExist(spacePersist.find(input.spaceId)),
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
      viewer.id
    )

    requestPersist.insert(requestEntity) match {
      case true => Success(requestEntity) map Request.of
      case false => Failure(CannotCreateSpaceException)
    }
  }

  def cancel(input: CancelRequestInput)
            (implicit viewer: Member): Try[Member] = {
    lazy val maybeRequestEntity = requestPersist.find(input.requestId)

    validateWith(
      Guard(maybeRequestEntity.isEmpty, RequestNotFoundException),
      Guard(!isMemberOwnRequest(viewer, maybeRequestEntity.get), NoPermissionException)
    ) {
      requestPersist.setStatus(input.requestId, RequestStatus.Cancelled.name) match {
        case true => Success(viewer)
        case false => Failure(CannotCancelRequestException)
      }
    }
  }


  private def isMemberOwnRequest(member: Member, request: RequestEntity) = {
    member.id == request.clientId
  }

}
