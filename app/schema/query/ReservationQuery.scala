package schema.query

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait ReservationQuery extends BaseModel {

  @GraphQLField
  def reservation(id: UUID)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.reservationFacade.find(id)
  }

}
