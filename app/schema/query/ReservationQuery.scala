package schema.query

import java.util.UUID

import models.{BaseModel, Reservation}
import sangria.macros.derive.GraphQLField

trait ReservationQuery extends BaseModel {

  @GraphQLField
  def reservation(id: UUID)(ctx: AppContext[Unit]): Option[Reservation] = resolveOption {
    ctx.ctx.reservationFacade.find(id)
  }

}
