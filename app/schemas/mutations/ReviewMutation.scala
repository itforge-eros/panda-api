package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.CreateReviewInput

trait ReviewMutation extends BaseModel {

  @GraphQLField
  def createReview(input: CreateReviewInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.reviewFacade.create(input)
  }

}
