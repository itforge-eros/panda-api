package schemas.queries

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait PermissionQuery extends BaseModel {

  @GraphQLField
  def permission(name: String)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.permissionFacade.findByName(name)
  }

}
