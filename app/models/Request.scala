package models

import java.time.Instant
import java.util.{Date, UUID}

import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

case class Request(id: UUID,
                   proposal: Option[String],
                   dates: List[Date],
                   period: Range,
                   createdAt: Instant,
                   @GraphQLExclude spaceId: UUID,
                   @GraphQLExclude clientId: UUID) {

  @GraphQLField
  def space(ctx: AppContext[Request]) = ctx.ctx.space.find(spaceId).get

  @GraphQLField
  def client(ctx: AppContext[Request]) = ctx.ctx.member.find(clientId).get

  @GraphQLField
  def reviews(ctx: AppContext[Request]) = ctx.ctx.review.findByRequestId(id)

}
