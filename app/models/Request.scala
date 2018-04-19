package models

import java.time.Instant
import java.util.{Date, UUID}

import entities.RequestEntity
import henkan.convert.Syntax._
import models.enums.RequestStatus
import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

case class Request(id: UUID,
                   body: Option[String],
                   dates: List[Date],
                   period: Range,
                   status: RequestStatus,
                   createdAt: Instant,
                   @GraphQLExclude spaceId: UUID,
                   @GraphQLExclude clientId: UUID) extends BaseModel {

  @GraphQLField
  def space(ctx: AppContext[Request]): Space = resolve {
    ctx.ctx.spaceFacade.find(spaceId)
  }

  @GraphQLField
  def client(ctx: AppContext[Request]): Member = resolve {
    ctx.ctx.memberFacade.find(clientId)
  }

  @GraphQLField
  def reviews(ctx: AppContext[Request]): List[Review] = authorize(ctx) { implicit viewer =>
    ctx.ctx.requestFacade.reviews(id)
  }

}

object Request {

  def of(requestEntity: RequestEntity): Request = Request(
    requestEntity.id,
    requestEntity.body,
    requestEntity.dates,
    requestEntity.period,
    RequestStatus(requestEntity.status),
    requestEntity.createdAt,
    requestEntity.spaceId,
    requestEntity.clientId
  )

}
