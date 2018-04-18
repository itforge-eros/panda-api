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
    extends Permission("ADMIN_ACCESS", "Provides full access to any resources", List(
      SpaceReadAccess,
      SpaceCreateAccess,
      RequestReadAccess,
      RequestCreateAccess,
    ))

  object SpaceFullAccessPermission
    extends Permission("SPACE_FULL_ACCESS", "Provides full access to spaces", List(
      SpaceCreateAccess,
      SpaceReadAccess
    ))

  object SpaceReadOnlyAccessPermission
    extends Permission("SPACE_READ_ONLY_ACCESS", "Provides read only access to spaces", List(
      SpaceReadAccess
    ))

  object RequestCreateAccessPermission
    extends Permission("REQUEST_CREATE_ACCESS", "Provides request creation access", List(
      RequestCreateAccess,
      RequestReadAccess,
      SpaceReadAccess
    ))

  object RequestApprovalAccessPermission
    extends Permission("REVIEW_CREATE_ACCESS", "Provides review creation access", List(
      RequestReviewAccess
    ))

}
