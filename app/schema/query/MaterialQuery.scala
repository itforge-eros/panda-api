package schema.query

import java.util.UUID

import models.{BaseModel, Material}
import sangria.macros.derive.GraphQLField

trait MaterialQuery extends BaseModel {

  @GraphQLField
  def material(id: UUID)(ctx: AppContext[Unit]): Option[Material] = resolveOption {
    ctx.ctx.materialFacade.find(id)
  }

}
