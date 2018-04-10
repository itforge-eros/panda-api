package schemas.mutations

import models.{BaseModel, Space}
import sangria.macros.derive.GraphQLField
import schemas.inputs.SpaceInput

import scala.util.Try

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: SpaceInput)(ctx: AppContext[Unit]): Try[Space] =
    authorize(ctx) { implicit member =>
      ctx.ctx.spaceFacade.create(input)
    }

}
