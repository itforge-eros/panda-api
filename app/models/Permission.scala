package models

import models.enums.Access
import models.enums.Access._

case class Permission(name: String,
                      description: String,
                      accesses: List[Access]) extends BaseModel

object Permission {

  val values: List[Permission] = List(
    AdminAccessPermission,
    SpaceFullAccessPermission,
    SpaceReadOnlyPermission,
    RequestCreatePermission,
    ReviewCreatePermission,
    RoleFullAccessPermission,
    MaterialFullAccessPermission,
    ProblemReadPermission
  )

  def apply(s: String): Option[Permission] = values.find(s equalsIgnoreCase _.name)

  object AdminAccessPermission
    extends Permission("ADMIN_PERMISSION", "Provides full access to any resources", List(
      SpaceReadAccess,
      SpaceCreateAccess,
      SpaceUpdateAccess,
      SpaceDeleteAccess,
      SpaceImageUploadAccess,
      RequestReadAccess,
      RequestCreateAccess,
      ReviewCreateAccess,
      RoleCreateAccess,
      RoleUpdateAccess,
      RoleDeleteAccess,
      RoleAssignAccess,
      RoleRevokeAccess,
      ProblemReadAccess,
      MaterialCreateAccess,
      MaterialDeleteAccess
    ))

  object SpaceFullAccessPermission
    extends Permission("SPACE_FULL_ACCESS_PERMISSION", "Provides full access to spaces", List(
      SpaceReadAccess,
      SpaceCreateAccess,
      SpaceUpdateAccess,
      SpaceDeleteAccess,
      SpaceImageUploadAccess
    ))

  object SpaceReadOnlyPermission
    extends Permission("SPACE_READ_ONLY_PERMISSION", "Provides read only access to spaces", List(
      SpaceReadAccess
    ))

  object RequestCreatePermission
    extends Permission("REQUEST_CREATE_PERMISSION", "Provides request creation access", List(
      SpaceReadAccess,
      RequestCreateAccess
    ))

  object ReviewCreatePermission
    extends Permission("REVIEW_CREATE_PERMISSION", "Provides review creation access", List(
      SpaceReadAccess,
      RequestReadAccess,
      ReviewCreateAccess
    ))

  object RoleFullAccessPermission
    extends Permission("ROLE_FULL_ACCESS_PERMISSION", "Provides full access to roles", List(
      RoleCreateAccess,
      RoleUpdateAccess,
      RoleDeleteAccess,
      RoleAssignAccess,
      RoleRevokeAccess
    ))

  object MaterialFullAccessPermission
    extends Permission("MATERIAL_FULL_ACCESS_PERMISSION", "Provides full access to materials", List(
      MaterialCreateAccess,
      MaterialDeleteAccess
    ))

  object ProblemReadPermission
    extends Permission("PROBLEM_READ_PERMISSION", "Provides read access to materials", List(
      SpaceReadAccess,
      ProblemReadAccess
    ))

}
