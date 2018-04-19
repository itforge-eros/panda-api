package models

import java.util.UUID

import entities.{MaterialEntity, MultiLanguageString}
import sangria.macros.derive.GraphQLExclude

case class Material(id: UUID,
                    name: MultiLanguageString,
                    @GraphQLExclude departmentId: UUID) extends BaseModel

object Material {

  def of(entity: MaterialEntity): Material = Material(
    entity.id,
    entity.name,
    entity.departmentId
  )

}