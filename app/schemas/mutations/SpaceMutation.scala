package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.SpaceInput

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: SpaceInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.spaceFacade.create(input)
  }

}
