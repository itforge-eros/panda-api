package models

import java.time.Instant
import java.util.UUID

import entities.ReviewEntity
import henkan.convert.Syntax._
import sangria.macros.derive.{GraphQLExclude, GraphQLField}
import utils.graphql.GraphqlUtil.AppContext

case class Review(@GraphQLExclude requestId: UUID,
                  @GraphQLExclude reviewerId: UUID,
                  description: Option[String],
                  isApproval: Boolean,
                  createdAt: Instant) {

  @GraphQLField
  def request(ctx: AppContext[Review]): Request = ctx.ctx.request.find(requestId).get

  @GraphQLField
  def reviewer(ctx: AppContext[Review]): Member = ctx.ctx.member.find(reviewerId).get

}

object Review {

  def of(reviewEntity: ReviewEntity): Review = reviewEntity.to[Review]()

}
