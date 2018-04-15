package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.{CreateRequestInput, CreateReviewInput}

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: CreateRequestInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.requestFacade.create(input)
  }

}
