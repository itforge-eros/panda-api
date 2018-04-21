package schema.mutation

import models.{BaseModel, Material}
import models.inputs.CreateMaterialInput
import sangria.macros.derive.GraphQLField

trait MaterialMutation extends BaseModel {

  @GraphQLField
  def createMaterial(input: CreateMaterialInput)(ctx: AppContext[Unit]): Option[Material] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.materialFacade.create(input)
  }

}
