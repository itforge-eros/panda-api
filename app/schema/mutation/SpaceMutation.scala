package schema.mutation

import models.{BaseModel, Space}
import models.inputs.{CreateSpaceInput, UpdateSpaceInput}
import sangria.macros.derive.GraphQLField

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: CreateSpaceInput)(ctx: AppContext[Unit]): Option[Space] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.spaceFacade.create(input)
  }

  @GraphQLField
  def updateSpace(input: UpdateSpaceInput)(ctx: AppContext[Unit]): Option[Space] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.spaceFacade.update(input)
  }

}
