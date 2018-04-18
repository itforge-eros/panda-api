package schema.mutation

import models.{BaseModel, Request}
import models.inputs.CreateRequestInput
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: CreateRequestInput)(ctx: AppContext[Unit]): Option[Request] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.requestFacade.create(input)
  }

}
