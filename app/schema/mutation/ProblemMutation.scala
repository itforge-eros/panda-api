package schema.mutation

import models.{BaseModel, Problem}
import models.inputs.CreateProblemInput
import sangria.macros.derive.GraphQLField

trait ProblemMutation extends BaseModel {

  @GraphQLField
  def createProblem(input: CreateProblemInput)(ctx: AppContext[Unit]): Option[Problem] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.problemFacade.create(input)
  }

}
