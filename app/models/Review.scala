package models

import java.time.Instant
import java.util.UUID

import entities.ReviewEntity
import henkan.convert.Syntax._
import sangria.macros.derive.{GraphQLExclude, GraphQLField}
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

case class Review(@GraphQLExclude requestId: UUID,
                  @GraphQLExclude reviewerId: UUID,
                  description: Option[String],
                  isApproval: Boolean,
                  createdAt: Instant) extends BaseModel {

  @GraphQLField
  def request(ctx: AppContext[Review]): Request =
    ctx.ctx.requestPersist.find(requestId) map Request.of get

  @GraphQLField
  def reviewer(ctx: AppContext[Review]): Try[Member] =
    ctx.ctx.memberFacade.find(reviewerId)

}

object Review {

  def of(reviewEntity: ReviewEntity): Review = reviewEntity.to[Review]()

}
