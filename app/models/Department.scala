package models

import java.time.Instant
import java.util.UUID

import entities.DepartmentEntity
import henkan.convert.Syntax._
import sangria.macros.derive.GraphQLField

case class Department(id: UUID,
                      name: String,
                      fullEnglishName: String,
                      fullThaiName: String,
                      description: Option[String],
                      createdAt: Instant,
                      updatedAt: Instant) extends BaseModel {

  @GraphQLField
  def roles(ctx: AppContext[Department]): List[Role] = authorize(ctx) { implicit identity =>
    ctx.ctx.departmentFacade.roles(id)
  }

  @GraphQLField
  def spaces(ctx: AppContext[Department]): List[Space] = resolve {
    ctx.ctx.departmentFacade.spaces(id)
  }

  @GraphQLField
  def materials(ctx: AppContext[Department]): List[Material] = resolve {
    ctx.ctx.departmentFacade.materials(id)
  }

}

object Department {

  def of(departmentEntity: DepartmentEntity): Department = departmentEntity.to[Department]()

}
