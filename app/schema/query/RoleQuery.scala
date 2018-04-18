package schema.query

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait RoleQuery extends BaseModel {

  @GraphQLField
  def role(department: String, name: String)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.roleFacade.findByName(department, name)
  }

}
