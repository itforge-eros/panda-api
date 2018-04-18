package schema.mutation

import models.{BaseModel, Space}
import models.inputs.CreateSpaceInput
import sangria.macros.derive.GraphQLField

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: CreateSpaceInput)(ctx: AppContext[Unit]): Option[Space] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.spaceFacade.create(input)
  }

}
