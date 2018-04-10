package models

import java.util.{Date, UUID}

import entities.ReservationEntity
import sangria.macros.derive.{GraphQLExclude, GraphQLField}
import henkan.convert.Syntax._
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

case class Reservation(id: UUID,
                       date: Date,
                       period: Range,
                       isAttended: Boolean,
                       @GraphQLExclude spaceId: UUID,
                       @GraphQLExclude clientId: UUID) extends BaseModel {

  @GraphQLField
  def space(ctx: AppContext[Request]): Try[Space] =
    ctx.ctx.spaceFacade.find(spaceId)

  @GraphQLField
  def client(ctx: AppContext[Request]): Try[Member] =
    ctx.ctx.memberFacade.find(clientId)

}

object Reservation {

  def of(reservationEntity: ReservationEntity): Reservation = reservationEntity.to[Reservation]()

}
