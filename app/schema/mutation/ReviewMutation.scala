package schema.mutation

import models.BaseModel
import models.inputs.CreateReviewInput
import sangria.macros.derive.GraphQLField

trait ReviewMutation extends BaseModel {

  @GraphQLField
  def createReview(input: CreateReviewInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.reviewFacade.create(input)
  }

}
