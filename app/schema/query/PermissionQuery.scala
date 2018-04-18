package schema.query

import models.{BaseModel, Permission}
import sangria.macros.derive.GraphQLField

trait PermissionQuery extends BaseModel {

  @GraphQLField
  def permission(name: String)(ctx: AppContext[Unit]): Option[Permission] = resolveOption {
    ctx.ctx.permissionFacade.find(name)
  }

  @GraphQLField
  def permissions(ctx: AppContext[Unit]): List[Permission] = {
    ctx.ctx.permissionFacade.permissions
  }

}
