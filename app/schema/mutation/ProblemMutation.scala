package schema.mutation

import models.{BaseModel, Problem}
import models.inputs.{CreateProblemInput, UpdateProblemInput}
import sangria.macros.derive.GraphQLField

trait ProblemMutation extends BaseModel {

  @GraphQLField
  def createProblem(input: CreateProblemInput)(ctx: AppContext[Unit]): Option[Problem] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.problemFacade.create(input)
  }

  @GraphQLField
  def updateProblem(input: UpdateProblemInput)(ctx: AppContext[Unit]): Option[Problem] = authorizeOption(ctx) { implicit identity =>
    ctx.ctx.problemFacade.update(input)
  }

}
