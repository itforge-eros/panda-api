package schema.mutation

import models.inputs.{CreateMaterialInput, DeleteMaterialInput}
import models.{BaseModel, Department, Material}
import sangria.macros.derive.GraphQLField

trait MaterialMutation extends BaseModel {

  @GraphQLField
  def createMaterial(input: CreateMaterialInput)(ctx: AppContext[Unit]): Option[Material] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.materialFacade.create(input)
  }

  @GraphQLField
  def deleteMaterial(input: DeleteMaterialInput)(ctx: AppContext[Unit]): Option[Department] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.materialFacade.delete(input)
  }

}
