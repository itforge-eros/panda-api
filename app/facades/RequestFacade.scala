package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.RequestException.RequestNotFoundException
import definitions.exceptions.SpaceException.{CannotCreateSpaceException, SpaceNotFoundException}
import entities.{MemberEntity, RequestEntity}
import models.enums.RequestStatus
import models.enums.RequestStatus.Pending
import models.inputs.CreateRequestInput
import models.{Member, Request, Review}
import persists.{RequestPersist, ReviewPersist, SpacePersist}

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RequestFacade(requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist,
                    spacePersist: SpacePersist) extends BaseFacade {

  def find(id: UUID)
          (implicit member: Member): Try[Request] = validateWith() {
    requestPersist.find(id)
      .toTry(RequestNotFoundException)
      .filterElse(isMemberOwnRequest(member, _))(NoPermissionException)
      .map(Request.of)
  }

  def reviews(id: UUID)
             (implicit member: Member): Try[List[Review]] = validate() {
    reviewPersist.findByRequestId(id) map Review.of
  }

  def create(input: CreateRequestInput)
            (implicit member: Member): Try[Request] = validateWith(
    Guard(spacePersist.find(input.spaceId) isEmpty, SpaceNotFoundException)
  ) {
    lazy val requestEntity = RequestEntity(
      UUID.randomUUID(),
      input.body,
      input.dates,
      input.period.toRange,
      Pending.name,
      Instant.now(),
      input.spaceId,
      member.id
    )

    requestPersist.insert(requestEntity) match {
      case true => Success(requestEntity) map Request.of
      case false => Failure(CannotCreateSpaceException)
    }
  }

  def cancel(id: UUID)
            (implicit member: Member): Try[Member] = {
    lazy val maybeRequestEntity = requestPersist.find(id)

    validateWith(
      Guard(maybeRequestEntity.isEmpty, RequestNotFoundException),
      Guard(!isMemberOwnRequest(member, maybeRequestEntity.get), NoPermissionException)
    ) {
      requestPersist.setStatus(id, RequestStatus.Cancelled.name) match {
        case true => Success(member)
      }
    }
  }


  private def isMemberOwnRequest(member: Member, request: RequestEntity) = {
    member.id == request.clientId
  }

}
