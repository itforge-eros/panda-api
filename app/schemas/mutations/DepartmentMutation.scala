package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.CreateDepartmentInput

trait DepartmentMutation extends BaseModel {

  @GraphQLField
  def createDepartment(input: CreateDepartmentInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.departmentFacade.create(input)
  }

}
