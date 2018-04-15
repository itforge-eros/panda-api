package schemas.queries

import java.util.UUID

import models.{BaseModel, Permission}
import sangria.macros.derive.GraphQLField

import scala.util.Success

trait PermissionQuery extends BaseModel {

  @GraphQLField
  def permission(id: UUID)(ctx: AppContext[Unit]) = resolveOption {
    Success(Permission(
      UUID.randomUUID(),
      "SPACE_FULL_ACCESS",
      "Provides full access to space management"
    ))
  }

}
