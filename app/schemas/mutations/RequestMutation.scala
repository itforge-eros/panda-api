package schemas.mutations

import models.{BaseModel, Request}
import sangria.macros.derive.GraphQLField
import schemas.inputs.RequestInput

import scala.language.postfixOps
import scala.util.Try

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: RequestInput)(ctx: AppContext[Unit]) =
    authorize(ctx) { implicit member =>
      ctx.ctx.requestFacade.insert(input)
    }

}
