package facades

import java.time.Instant
import java.util.UUID

import entities.RequestEntity
import models.{Member, Request, Review}
import persists.{RequestPersist, ReviewPersist}
import schemas.inputs.RequestInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RequestFacade(requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist) extends BaseFacade {

  def find(id: UUID)
          (implicit member: Member): Try[Request] = {
    Try(requestPersist.find(id))
      .flatMap(_.toTry(RequestNotFoundException))
      .map(Request.of)
      .filterElse(_.clientId == member.id)(NoPermissionException)
  }

  def reviews(id: UUID)
             (implicit member: Member): Try[List[Review]] = {
    Try(reviewPersist.findByRequestId(id) map Review.of)
  }

  def insert(input: RequestInput)
            (implicit member: Member): Try[Request] = {
    lazy val requestEntity = RequestEntity(
      UUID.randomUUID(),
      input.proposal,
      input.dates,
      input.period.toRange,
      Instant.now(),
      input.spaceId,
      member.id
    )

    Try(requestPersist.insert(requestEntity))
      .flatMap(if (_) Success(requestEntity) else Failure(CannotCreateSpaceException))
      .map(Request.of)
  }

}
