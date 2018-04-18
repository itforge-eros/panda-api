package models

import java.util.{Date, UUID}

import entities.ReservationEntity
import henkan.convert.Syntax._
import sangria.macros.derive.{GraphQLExclude, GraphQLField}

import scala.language.postfixOps
import scala.util.Try

case class Reservation(id: UUID,
                       date: Date,
                       period: Range,
                       isAttended: Boolean,
                       @GraphQLExclude spaceId: UUID,
                       @GraphQLExclude clientId: UUID) extends BaseModel {

  @GraphQLField
  def space(ctx: AppContext[Reservation]): Space = resolve {
    ctx.ctx.spaceFacade.find(spaceId)
  }

  @GraphQLField
  def client(ctx: AppContext[Reservation]): Member = resolve {
    ctx.ctx.memberFacade.find(clientId)
  }

}

object Reservation {

  def of(reservationEntity: ReservationEntity): Reservation = reservationEntity.to[Reservation]()

}
