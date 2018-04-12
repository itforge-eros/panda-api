package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.{RequestInput, ReviewInput}

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: RequestInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.requestFacade.create(input)
  }

  @GraphQLField
  def approveRequest(input: ReviewInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.reviewFacade.create(input, isApproval = true)
  }

  @GraphQLField
  def rejectRequest(input: ReviewInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.reviewFacade.create(input, isApproval = false)
  }

}
