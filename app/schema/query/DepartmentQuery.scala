package schema.query

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait DepartmentQuery extends BaseModel {

  @GraphQLField
  def department(name: String)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.departmentFacade.findByName(name)
  }

}
