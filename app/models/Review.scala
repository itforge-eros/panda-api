package models

import java.time.Instant
import java.util.UUID

import entities.ReviewEntity
import models.enums.ReviewEvent
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

import scala.language.postfixOps

case class Review(id: UUID,
                  body: Option[String],
                  event: ReviewEvent,
                  createdAt: Instant,
                  @GraphQLExclude requestId: UUID,
                  @GraphQLExclude reviewerId: UUID) extends BaseModel {

  @GraphQLField
  def request(ctx: AppContext[Review]): Request = authorize(ctx) { implicit identity =>
    ctx.ctx.requestFacade.find(requestId)
  }

  @GraphQLField
  def reviewer(ctx: AppContext[Review]): Member = resolve {
    ctx.ctx.memberFacade.find(reviewerId)
  }

}

object Review {

  def of(entity: ReviewEntity): Review = Review(
    entity.id,
    entity.body,
    ReviewEvent(entity.event).get,
    entity.createdAt,
    entity.requestId,
    entity.reviewerId
  )

}
