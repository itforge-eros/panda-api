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
    SpaceReadOnlyAccessPermission,
    RequestCreateAccessPermission,
    RequestApprovalAccessPermission
  )

  def apply(s: String): Option[Permission] = values.find(s equalsIgnoreCase _.name)

  object AdminAccessPermission
    extends Permission("ADMIN_PERMISSION", "Provides full access to any resources", List(
      SpaceReadAccess,
      SpaceCreateAccess,
      SpaceUpdateAccess,
      RequestReadAccess,
      RequestCreateAccess,
      ReviewCreateAccess,
      RoleCreateAccess,
      RoleUpdateAccess,
      RoleAssignAccess,
      ProblemReadAccess,
      MaterialCreateAccess,
      MaterialDeleteAccess
    ))

  object SpaceFullAccessPermission
    extends Permission("SPACE_FULL_ACCESS_PERMISSION", "Provides full access to spaces", List(
      SpaceCreateAccess,
      SpaceUpdateAccess,
      SpaceReadAccess
    ))

  object SpaceReadOnlyAccessPermission
    extends Permission("SPACE_READ_ONLY_PERMISSION", "Provides read only access to spaces", List(
      SpaceReadAccess
    ))

  object RequestCreateAccessPermission
    extends Permission("REQUEST_CREATE_PERMISSION", "Provides request creation access", List(
      RequestCreateAccess,
      RequestReadAccess,
      SpaceReadAccess
    ))

  object RequestApprovalAccessPermission
    extends Permission("REVIEW_CREATE_PERMISSION", "Provides review creation access", List(
      ReviewCreateAccess
    ))

}
