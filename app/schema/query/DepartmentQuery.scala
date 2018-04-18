package schema.query

import models.{BaseModel, Department}
import sangria.macros.derive.GraphQLField

trait DepartmentQuery extends BaseModel {

  @GraphQLField
  def department(name: String)(ctx: AppContext[Unit]): Option[Department] = resolveOption {
    ctx.ctx.departmentFacade.findByName(name)
  }

}
