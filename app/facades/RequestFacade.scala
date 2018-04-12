package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AppException._
import entities.RequestEntity
import models.{Member, Request, Review}
import persists.{RequestPersist, ReviewPersist}
import schemas.inputs.RequestInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RequestFacade(requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist) extends BaseFacade {

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

  def create(input: RequestInput)
            (implicit member: Member): Try[Request] = ValidateWith() {
    lazy val requestEntity = RequestEntity(
      UUID.randomUUID(),
      input.proposal,
      input.dates,
      input.period.toRange,
      Instant.now(),
      input.spaceId,
      member.id
    )

    requestPersist.insert(requestEntity) match {
      case true => Success(Request.of(requestEntity))
      case false => Failure(CannotCreateSpaceException)
    }
  }

}
