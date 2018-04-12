package facades

import java.time.Instant

import definitions.exceptions.AppException._
import entities.ReviewEntity
import models.{Member, Review}
import persists.{RequestPersist, ReviewPersist}
import schemas.inputs.ReviewInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class ReviewFacade(reviewPersist: ReviewPersist,
                   requestPersist: RequestPersist) extends BaseFacade {

  def create(input: ReviewInput, isApproval: Boolean)
            (implicit member: Member): Try[Review] = ValidateWith(
    Guard(requestPersist.find(input.requestId) isEmpty, RequestNotFoundException),
    Guard(reviewPersist.find(input.requestId, member.id) isDefined, ReviewAlreadyExistedException)
  ) {
    val reviewEntity = ReviewEntity(
      input.requestId,
      member.id,
      input.description,
      isApproval,
      Instant.now()
    )

    reviewPersist.insert(reviewEntity) match {
      case true => Success(Review.of(reviewEntity))
      case false => Failure(CannotCreateReviewException)
    }
  }

}
