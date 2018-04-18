package schema.mutation

import models.BaseModel
import models.inputs.CreateRoleInput
import sangria.macros.derive.GraphQLField

trait RoleMutation extends BaseModel {

  @GraphQLField
  def create(roleInput: CreateRoleInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.roleFacade.create(roleInput)
  }

}
