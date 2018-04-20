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

}
