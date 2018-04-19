package schema.mutation

import java.util.UUID

import models.{BaseModel, Member, Request}
import models.inputs.CreateRequestInput
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait RequestMutation extends BaseModel {

  @GraphQLField
  def createRequest(input: CreateRequestInput)(ctx: AppContext[Unit]): Option[Request] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.requestFacade.create(input)
  }

  @GraphQLField
  def cancelRequest(id: UUID)(ctx: AppContext[Unit]): Option[Member] = authorizeOption(ctx) { implicit member =>
    ctx.ctx.requestFacade.cancel(id)
  }

}
