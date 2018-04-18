package models

import models.enums.Access
import models.enums.Access._

case class Permission(name: String,
                      description: String,
                      accesses: List[Access]) extends BaseModel

object Permission {

  var values: List[Permission] = List(
    AdminAccessPermission,
    SpaceFullAccessPermission,
    SpaceReadOnlyAccessPermission,
    RequestCreateAccessPermission,
    RequestApprovalAccessPermission
  )

  def apply(name: String): Option[Permission] = values.find(name == _.name)

  object AdminAccessPermission
    extends Permission("ADMIN_PERMISSION", "Provides full access to any resources", List(
      SpaceCreateAccess,
      SpaceReadAccess,
      RequestCreateAccess
    ))

  object SpaceFullAccessPermission
    extends Permission("SPACE_FULL_ACCESS_PERMISSION", "Provides full access to spaces", List(
      SpaceCreateAccess,
      SpaceReadAccess
    ))

  object SpaceReadOnlyAccessPermission
    extends Permission("SPACE_READ_ONLY_PERMISSION", "Provides read only access to spaces", List(
      SpaceReadAccess
    ))

  object RequestCreateAccessPermission
    extends Permission("REQUEST_CREATE_PERMISSION", "Provides request creation access to spaces", List(
      RequestCreateAccess,
      SpaceReadAccess
    ))

  object RequestApprovalAccessPermission
    extends Permission("APPROVER_PERMISSION", "Provides approval access to requests", List(
      RequestApproveAccess
    ))

}
