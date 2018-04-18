package schema.mutation

import models.BaseModel
import models.inputs.CreateDepartmentInput
import sangria.macros.derive.GraphQLField

trait DepartmentMutation extends BaseModel {

  @GraphQLField
  def createDepartment(input: CreateDepartmentInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.departmentFacade.create(input)
  }

}
