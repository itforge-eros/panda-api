package models

import java.time.Instant
import java.util.UUID

import entities.{MaterialEntity, MultiLanguageString}
import sangria.macros.derive.GraphQLExclude

case class Material(id: UUID,
                    name: MultiLanguageString,
                    createdAt: Instant,
                    @GraphQLExclude departmentId: UUID) extends BaseModel {

  def department(ctx: AppContext[Material]): Department = resolve {
    ctx.ctx.departmentFacade.find(departmentId)
  }

}

object Material {

  def of(entity: MaterialEntity): Material = Material(
    entity.id,
    entity.name,
    entity.createdAt,
    entity.departmentId
  )

}
