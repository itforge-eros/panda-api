package facades

import java.time.Instant
import java.util.UUID

import entities.RequestEntity
import models.{Member, Request, Review}
import persists.{RequestPersist, ReviewPersist}
import schemas.inputs.RequestInput

import scala.language.postfixOps
import scala.util.Try

class RequestFacade(requestPersist: RequestPersist,
                    reviewPersist: ReviewPersist) extends BaseFacade {

  def find(id: UUID,
           member: Member): Try[Request] = {
    lazy val request = requestPersist.find(id)

    TryWith(
      Guard(request.isEmpty, RequestNotFoundException),
      Guard(member.id == request.get.clientId, new AccessDeniedException("Cannot access request details."))
    ) {
      requestPersist.find(id) map Request.of get
    }
  }

  def insert(input: RequestInput)
            (implicit member: Member): Try[Request] = Try {
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
      case true => Request.of(requestEntity)
      case false => throw CannotCreateRequestException
    }
  }

  def reviews(id: UUID)
             (implicit member: Member): Try[List[Review]] = Try {
    reviewPersist.findByRequestId(id) map Review.of
  }

}
