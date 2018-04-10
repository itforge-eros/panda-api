package models

import java.time.Instant
import java.util.{Date, UUID}

import entities.RequestEntity
import henkan.convert.Syntax._
import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

case class Request(id: UUID,
                   proposal: Option[String],
                   dates: List[Date],
                   period: Range,
                   createdAt: Instant,
                   @GraphQLExclude spaceId: UUID,
                   @GraphQLExclude clientId: UUID) extends BaseModel {

  @GraphQLField
  def space(ctx: AppContext[Request]): Try[Space] = {
    ctx.ctx.spaceFacade.find(spaceId)
  }

  @GraphQLField
  def client(ctx: AppContext[Request]): Try[Member] =
    ctx.ctx.memberFacade.find(clientId)

  @GraphQLField
  def reviews(ctx: AppContext[Request]): Try[List[Review]] =
    authorize(ctx) { implicit member =>
      ctx.ctx.requestFacade.reviews(id)
    }

}

object Request {

  def of(requestEntity: RequestEntity): Request = requestEntity.to[Request]()

}
