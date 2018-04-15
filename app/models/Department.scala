package models

import java.util.UUID

import entities.DepartmentEntity
import henkan.convert.Syntax._
import sangria.macros.derive.GraphQLField

case class Department(id: UUID,
                      name: String,
                      description: Option[String]) extends BaseModel {

  @GraphQLField
  def roles(ctx: AppContext[Department]) = authorize(ctx) { implicit member =>
    ctx.ctx.departmentFacade.roles(id)
  }

  @GraphQLField
  def spaces(ctx: AppContext[Department]) = resolve {
    ctx.ctx.departmentFacade.spaces(id)
  }

}

object Department {

  def of(departmentEntity: DepartmentEntity): Department = departmentEntity.to[Department]()

}
