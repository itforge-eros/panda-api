package schema.mutation

import models.inputs.{CancelRequestInput, CreateRequestInput}
import models.{BaseModel, Member, Request}
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: CreateRequestInput)(ctx: AppContext[Unit]): Option[Request] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.requestFacade.create(input)
  }

  @GraphQLField
  def cancelRequest(input: CancelRequestInput)(ctx: AppContext[Unit]): Option[Member] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.requestFacade.cancel(input)
  }

}
