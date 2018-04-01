package models

import java.time.Instant
import java.util.{Date, UUID}

import entities.RequestEntity
import henkan.convert.Syntax._
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
  def space(ctx: AppContext[Request]): Space = ctx.ctx.space.find(spaceId).get

  @GraphQLField
  def client(ctx: AppContext[Request]): Member = ctx.ctx.member.find(clientId).get

  @GraphQLField
  def reviews(ctx: AppContext[Request]): List[Review] = ctx.ctx.review.findByRequestId(id)

}

object Request {

  def of(requestEntity: RequestEntity): Request = requestEntity.to[Request]()

}
