package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.RequestException.RequestNotFoundException
import definitions.exceptions.SpaceException.{CannotCreateSpaceException, SpaceNotFoundException}
import entities.RequestEntity
import models.enums.RequestStatus.Pending
import models.{Member, Request, Review}
import persists.{RequestPersist, ReviewPersist, SpacePersist}
import schemas.inputs.CreateRequestInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RequestFacade(requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist,
                    spacePersist: SpacePersist) extends BaseFacade {

  def find(id: UUID)
          (implicit member: Member): Try[Request] = ValidateWith() {
    requestPersist.find(id)
      .toTry(RequestNotFoundException)
      .filterElse(_.clientId == member.id)(NoPermissionException)
      .map(Request.of)
  }

  def reviews(id: UUID)
             (implicit member: Member): Try[List[Review]] = Validate() {
    reviewPersist.findByRequestId(id) map Review.of
  }

  def create(input: CreateRequestInput)
            (implicit member: Member): Try[Request] = ValidateWith(
    Guard(spacePersist.find(input.spaceId) isEmpty, SpaceNotFoundException)
  ) {
    lazy val requestEntity = RequestEntity(
      UUID.randomUUID(),
      input.proposal,
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

}
