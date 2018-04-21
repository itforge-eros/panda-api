package schema.query

import java.util.UUID

import models.{BaseModel, Request}
import sangria.macros.derive.GraphQLField

import scala.util.Try

trait RequestQuery extends BaseModel {

  @GraphQLField
  def request(id: UUID)(ctx: AppContext[Unit]): Option[Request] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.requestFacade.find(id)
  }

}
