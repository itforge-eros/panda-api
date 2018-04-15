package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait PermissionQuery extends BaseModel {

  @GraphQLField
  def permission(id: UUID)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.permissionFacade.find(id)
  }

}
