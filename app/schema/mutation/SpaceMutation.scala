package schema.mutation

import models.{BaseModel, Department, Space}
import models.inputs.{CreateSpaceInput, DeleteSpaceInput, UpdateSpaceInput, UploadSpaceImageInput}
import sangria.macros.derive.GraphQLField

import scala.util.Success

trait SpaceMutation extends BaseModel {

  @GraphQLField
  def createSpace(input: CreateSpaceInput)(ctx: AppContext[Unit]): Option[Space] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.spaceFacade.create(input)
  }

  @GraphQLField
  def updateSpace(input: UpdateSpaceInput)(ctx: AppContext[Unit]): Option[Space] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.spaceFacade.update(input)
  }

  @GraphQLField
  def deleteSpace(input: DeleteSpaceInput)(ctx: AppContext[Unit]): Option[Department] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.spaceFacade.delete(input)
  }

  @GraphQLField
  def uploadSpaceImage(input: UploadSpaceImageInput)(ctx: AppContext[Unit]): Option[String] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.spaceFacade.uploadImage(input)
  }

}
