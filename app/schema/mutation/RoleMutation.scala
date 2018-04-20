package schema.mutation

import models.{BaseModel, Role}
import models.inputs.{AssignRoleInput, CreateRoleInput}
import sangria.macros.derive.GraphQLField

trait RoleMutation extends BaseModel {

  @GraphQLField
  def createRole(input: CreateRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.roleFacade.create(input)
  }

  @GraphQLField
  def assignRole(input: AssignRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.roleFacade.assignRole(input)
  }

}
