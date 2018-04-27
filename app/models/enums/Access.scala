package models.enums

import sangria.macros.derive.GraphQLName

sealed trait Access {

  val name: String

}

object Access {

  @GraphQLName(SpaceReadAccess.name)
  case object SpaceReadAccess extends Access {
    override val name = "SPACE_READ_ACCESS"
  }

  @GraphQLName(SpaceCreateAccess.name)
  case object SpaceCreateAccess extends Access {
    override val name = "SPACE_CREATE_ACCESS"
  }

  @GraphQLName(SpaceUpdateAccess.name)
  case object SpaceUpdateAccess extends Access {
    override val name = "SPACE_UPDATE_ACCESS"
  }

  @GraphQLName(SpaceDeleteAccess.name)
  case object SpaceDeleteAccess extends Access {
    override val name = "SPACE_DELETE_ACCESS"
  }

  @GraphQLName(SpaceImageUploadAccess.name)
  case object SpaceImageUploadAccess extends Access {
    override val name = "SPACE_IMAGE_UPLOAD_ACCESS"
  }

  @GraphQLName(RequestReadAccess.name)
  case object RequestReadAccess extends Access {
    override val name = "REQUEST_READ_ACCESS"
  }

  @GraphQLName(RequestCreateAccess.name)
  case object RequestCreateAccess extends Access {
    override val name = "REQUEST_CREATE_ACCESS"
  }

  @GraphQLName(ReviewCreateAccess.name)
  case object ReviewCreateAccess extends Access {
    override val name = "REVIEW_CREATE_ACCESS"
  }

  @GraphQLName(RoleCreateAccess.name)
  case object RoleCreateAccess extends Access {
    override val name = "ROLE_CREATE_ACCESS"
  }

  @GraphQLName(RoleUpdateAccess.name)
  case object RoleUpdateAccess extends Access {
    override val name = "ROLE_UPDATE_ACCESS"
  }

  @GraphQLName(RoleDeleteAccess.name)
  case object RoleDeleteAccess extends Access {
    override val name = "ROLE_DELETE_ACCESS"
  }

  @GraphQLName(RoleAssignAccess.name)
  case object RoleAssignAccess extends Access {
    override val name = "ROLE_ASSIGN_ACCESS"
  }

  @GraphQLName(RoleRevokeAccess.name)
  case object RoleRevokeAccess extends Access {
    override val name = "ROLE_REVOKE_ACCESS"
  }

  @GraphQLName(ProblemReadAccess.name)
  case object ProblemReadAccess extends Access {
    override val name = "PROBLEM_READ_ACCESS"
  }

  @GraphQLName(ProblemUpdateAccess.name)
  case object ProblemUpdateAccess extends Access {
    override val name = "PROBLEM_UPDATE_ACCESS"
  }

  @GraphQLName(MaterialCreateAccess.name)
  case object MaterialCreateAccess extends Access {
    override val name = "MATERIAL_CREATE_ACCESS"
  }

  @GraphQLName(MaterialDeleteAccess.name)
  case object MaterialDeleteAccess extends Access {
    override val name = "MATERIAL_DELETE_ACCESS"
  }

}
