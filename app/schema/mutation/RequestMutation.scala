package schema.mutation

import java.util.UUID

import models.{BaseModel, Member, Request}
import models.inputs.{CancelRequestInput, CreateRequestInput}
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: CreateRequestInput)(ctx: AppContext[Unit]): Option[Request] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.requestFacade.create(input)
  }

  @GraphQLField
  def cancelRequest(input: CancelRequestInput)(ctx: AppContext[Unit]): Option[Member] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.requestFacade.cancel(input)
  }

}
