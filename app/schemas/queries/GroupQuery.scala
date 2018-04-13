package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait GroupQuery extends BaseModel {

  @GraphQLField
  def group(id: UUID)(ctx: AppContext[Unit]) = resolve {
    ctx.ctx.groupFacade.find(id)
  }

}
