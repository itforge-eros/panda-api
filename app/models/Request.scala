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
                   materials: List[String],
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
  def reviews(ctx: AppContext[Request]): List[Review] = authorize(ctx) { implicit identity =>
    ctx.ctx.requestFacade.reviews(id)
  }

}

object Request {

  def of(entity: RequestEntity): Request = Request(
    entity.id,
    entity.body,
    entity.dates,
    entity.period,
    RequestStatus(entity.status),
    entity.materials,
    entity.createdAt,
    entity.spaceId,
    entity.clientId
  )

}
