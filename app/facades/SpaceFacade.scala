package facades

import java.util.UUID

import definitions.AppException.SpaceNotFoundException
import models.Space
import persists.SpacePersist
import utils.Validation

import scala.language.postfixOps
import scala.util.Try

class SpaceFacade(spacePersist: SpacePersist) extends Validation {

  def find(id: UUID): Try[Space] = {
    lazy val maybeSpace = spacePersist.find(id)

    TryWith(
      Guard(maybeSpace.isEmpty, SpaceNotFoundException)
    ) {
      maybeSpace map Space.of get
    }
  }

  def findAll: Try[List[Space]] = TryWith() {
    spacePersist.findAll map Space.of
  }

}
