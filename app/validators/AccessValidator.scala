package validators

import java.util.UUID

import models.enums.Access
import models.enums.Access.SpaceUpdateAccess

object AccessValidator {

  def memberCanUpdateSpace(memberId: UUID, spaceId: UUID)
                          (implicit accesses: List[Access]): Boolean = {
    println(accesses map (_.name))
    println(SpaceUpdateAccess.name)
    accesses.exists(_.name == SpaceUpdateAccess.name)
  }

}
