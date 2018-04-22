package schema.mutation

import models.{BaseModel, Department, Role}
import models.inputs._
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
  def deleteRole(input: DeleteRoleInput)(ctx: AppContext[Unit]): Option[Department] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.roleFacade.delete(input)
  }

  @GraphQLField
  def assignRole(input: AssignRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.roleFacade.assign(input)
  }

  @GraphQLField
  def revokeRole(input: RevokeRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.roleFacade.revoke(input)
  }

}
