package models

import java.util.{Date, UUID}

import entities.ReservationEntity
import sangria.macros.derive.GraphQLExclude
import henkan.convert.Syntax._

case class Reservation(id: UUID,
                       date: Date,
                       period: Range,
                       isAttended: Boolean,
                       @GraphQLExclude spaceId: UUID,
                       @GraphQLExclude clientId: UUID)

object Reservation {

  def of(reservationEntity: ReservationEntity): Reservation = reservationEntity.to[Reservation]()

}
