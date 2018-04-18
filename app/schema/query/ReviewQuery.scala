package schema.query

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait ReviewQuery extends BaseModel {

  @GraphQLField
  def review(id: UUID)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.reviewFacade.find(id)
  }

}
