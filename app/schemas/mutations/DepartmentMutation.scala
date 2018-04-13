package schemas.mutations

import models.BaseModel
import sangria.macros.derive.GraphQLField
import schemas.inputs.DepartmentInput

trait DepartmentMutation extends BaseModel {

  @GraphQLField
  def createDepartment(input: DepartmentInput)(ctx: AppContext[Unit]) = authorize(ctx) { implicit member =>
    ctx.ctx.departmentFacade.create(input)
  }

}
