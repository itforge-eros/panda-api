package schema.mutation

import models.BaseModel
import models.inputs.CreateRequestInput
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: CreateRequestInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.requestFacade.create(input)
  }

}
