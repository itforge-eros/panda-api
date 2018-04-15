package models

import models.Access._

case class Permission(name: String,
                      description: String) extends BaseModel

object Permission {

  var values: List[Permission] = List(
    AdminAccessPermission,
    SpaceFullAccessPermission,
    SpaceReadOnlyAccessPermission,
    RequestCreateAccessPermission,
    RequestApprovalAccessPermission
  )

  def fromName(name: String): Option[Permission] = values.find(name == _.name)

  object AdminAccessPermission
    extends Permission("ADMIN_ACCESS", "Provides full access to any resources")
      with SpaceCreateAccess
      with SpaceReadAccess
      with RequestCreateAccess

  object SpaceFullAccessPermission
    extends Permission("SPACE_FULL_ACCESS", "Provides full access to spaces")
      with SpaceCreateAccess
      with SpaceReadAccess

  object SpaceReadOnlyAccessPermission
    extends Permission("SPACE_READ_ONLY_ACCESS", "Provides read only access to spaces")
      with SpaceReadAccess

  object RequestCreateAccessPermission
    extends Permission("REQUEST_CREATE_ACCESS_PERMISSION", "Provides request creation access to spaces")
      with RequestCreateAccess
      with SpaceReadAccess

  object RequestApprovalAccessPermission
    extends Permission("SPACE_APPROVAL_ACCESS", "Provides approval access to requests")
      with RequestApprovalAccess

}

object Access {

  trait SpaceReadAccess
  trait SpaceCreateAccess
  trait RequestCreateAccess
  trait RequestApprovalAccess

}
