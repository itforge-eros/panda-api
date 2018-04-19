package schema.query

import java.util.UUID

import models.{BaseModel, Review}
import sangria.macros.derive.GraphQLField

trait ReviewQuery extends BaseModel {

  @GraphQLField
  def review(id: UUID)(ctx: AppContext[Unit]): Option[Review] = authorizeOption(ctx) { implicit viewer =>
    ctx.ctx.reviewFacade.find(id)
  }

}
