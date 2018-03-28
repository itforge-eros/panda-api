package models

import java.time.Instant
import java.util.UUID

import sangria.macros.derive._
import utils.GraphqlUtil.AppContext

case class Request(id: UUID,
                   proposal: String,
                   createdAt: Instant,
                   @GraphQLExclude spaceId: UUID,
                   @GraphQLExclude clientId: UUID) {

  @GraphQLField
  def space(ctx: AppContext[Request]) = ctx.ctx.space.find(spaceId)

  @GraphQLField
  def client(ctx: AppContext[Request]) = ctx.ctx.member.find(clientId)

  @GraphQLField
  def approvals(ctx: AppContext[Request]) = ctx.ctx.approval.findByRequestId(id)

}
