package validators

import definitions.exceptions.SpaceException.SpaceNotFoundException
import entities.SpaceEntity
import utils.Guard

object SpaceValidator {

  def spaceExist(spaceEntity: Option[SpaceEntity]): Guard = {
    spaceEntity.isEmpty match {
      case true => Guard.failed(SpaceNotFoundException)
      case false => Guard.passed
    }
  }

}
