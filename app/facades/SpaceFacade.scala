package facades

import java.util.UUID

import models.{Member, Request, Space}
import persists.{RequestPersist, SpacePersist}

import scala.language.postfixOps
import scala.util.Try

class SpaceFacade(spacePersist: SpacePersist,
                  requestPersist: RequestPersist) extends BaseFacade {

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

  def incomingRequests(id: UUID)
                      (implicit member: Member): Try[List[Request]] = Try {
    requestPersist.findBySpaceId(id) map Request.of
  }

}
