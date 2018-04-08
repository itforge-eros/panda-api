package models

import java.util.{Date, UUID}

import entities.ReservationEntity
import sangria.macros.derive.{GraphQLExclude, GraphQLField}
import henkan.convert.Syntax._
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps

case class Reservation(id: UUID,
                       date: Date,
                       period: Range,
                       isAttended: Boolean,
                       @GraphQLExclude spaceId: UUID,
                       @GraphQLExclude clientId: UUID) {

  @GraphQLField
  def space(ctx: AppContext[Request]): Space =
    ctx.ctx.spacePersist.find(spaceId) map Space.of get

  @GraphQLField
  def client(ctx: AppContext[Request]): Member =
    ctx.ctx.memberPersist.find(clientId) map Member.of get

}

object Reservation {

  def of(reservationEntity: ReservationEntity): Reservation = reservationEntity.to[Reservation]()

}
