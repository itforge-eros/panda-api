package schema.mutation

import models.{BaseModel, Role}
import models.inputs.{AssignRoleInput, CreateRoleInput}
import sangria.macros.derive.GraphQLField

trait RoleMutation extends BaseModel {

  @GraphQLField
  def createRole(roleInput: CreateRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.roleFacade.create(roleInput)
  }

  @GraphQLField
  def assignRole(roleInput: AssignRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.roleFacade.assignRole(roleInput)
  }

}
