package schema.query

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait PermissionQuery extends BaseModel {

  @GraphQLField
  def permission(name: String)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.permissionFacade.find(name)
  }

  @GraphQLField
  def permissions(ctx: AppContext[Unit]) = ctx.ctx.permissionFacade.permissions

}
