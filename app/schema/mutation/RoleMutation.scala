package schema.mutation

import models.{BaseModel, Role}
import models.inputs.{AssignRoleInput, CreateRoleInput, UpdateRoleInput}
import sangria.macros.derive.GraphQLField

trait RoleMutation extends BaseModel {

  @GraphQLField
  def createRole(input: CreateRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.roleFacade.create(input)
  }

  @GraphQLField
  def updateRole(input: UpdateRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.roleFacade.update(input)
  }

  @GraphQLField
  def assignRole(input: AssignRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.roleFacade.assign(input)
  }

}
