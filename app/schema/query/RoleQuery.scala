package schema.query

import models.{BaseModel, Role}
import sangria.macros.derive.GraphQLField

trait RoleQuery extends BaseModel {

  @GraphQLField
  def role(department: String, name: String)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.roleFacade.findByName(department, name)
  }

}
