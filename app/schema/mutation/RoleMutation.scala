package schema.mutation

import models.{BaseModel, Role}
import models.inputs.CreateRoleInput
import sangria.macros.derive.GraphQLField

trait RoleMutation extends BaseModel {

  @GraphQLField
  def create(roleInput: CreateRoleInput)(ctx: AppContext[Unit]): Option[Role] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.roleFacade.create(roleInput)
  }

}
