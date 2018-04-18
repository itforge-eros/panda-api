package schema.query

import java.util.UUID

import models.{BaseModel, Request}
import sangria.macros.derive.GraphQLField

import scala.util.Try

trait RequestQuery extends BaseModel {

  @GraphQLField
  def request(id: UUID)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.requestFacade.find(id)
  }

}
