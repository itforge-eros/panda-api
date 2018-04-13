package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait RoleQuery extends BaseModel {

  @GraphQLField
  def role(id: UUID)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.roleFacade.find(id)
  }

}
