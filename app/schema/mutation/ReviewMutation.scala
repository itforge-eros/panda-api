package schema.mutation

import models.{BaseModel, Review}
import models.inputs.CreateReviewInput
import sangria.macros.derive.GraphQLField

trait ReviewMutation extends BaseModel {

  @GraphQLField
  def createReview(input: CreateReviewInput)(ctx: AppContext[Unit]): Option[Review] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.reviewFacade.create(input)
  }

}
