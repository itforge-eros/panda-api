package schema.mutation

import models.BaseModel
import models.inputs.CreateSpaceInput
import sangria.macros.derive.GraphQLField

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: CreateSpaceInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.spaceFacade.create(input)
  }

}
