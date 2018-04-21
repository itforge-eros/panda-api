package schema.query

import java.util.UUID

import models.{BaseModel, Problem}
import sangria.macros.derive.GraphQLField

trait ProblemQuery extends BaseModel {

  @GraphQLField
  def problem(id: UUID)(ctx: AppContext[Unit]): Option[Problem] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.problemFacade.find(id)
  }

}
