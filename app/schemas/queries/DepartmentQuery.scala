package schemas.queries

import java.util.UUID

import models.BaseModel
import sangria.macros.derive.GraphQLField

trait DepartmentQuery extends BaseModel {

  @GraphQLField
  def department(id: UUID)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.departmentFacade.find(id)
  }

}
