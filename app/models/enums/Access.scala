package models.enums

import sangria.macros.derive.GraphQLName

sealed trait Access

object Access {

  @GraphQLName("SPACE_READ_ACCESS")
  case object SpaceReadAccess extends Access

  @GraphQLName("SPACE_CREATE_ACCESS")
  case object SpaceCreateAccess extends Access

  @GraphQLName("REQUEST_CREATE_ACCESS")
  case object RequestCreateAccess extends Access

  @GraphQLName("REQUEST_APPROVE_ACCESS")
  case object RequestApproveAccess extends Access

}
