package schemas.queries

import java.util.UUID

import models.{BaseModel, Group}
import sangria.macros.derive.GraphQLField

import scala.util.Success

trait GroupQuery extends BaseModel {

  @GraphQLField
  def group(id: UUID)(ctx: AppContext[Unit]) = resolve {
    Success(Group(
      UUID.randomUUID(),
      "group name",
      Some("group description")
    ))
  }

}
