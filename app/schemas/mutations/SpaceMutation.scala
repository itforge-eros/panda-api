package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.CreateSpaceInput

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: CreateSpaceInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.spaceFacade.create(input)
  }

}
