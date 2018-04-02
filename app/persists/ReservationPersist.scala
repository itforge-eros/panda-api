package persists

import java.util.UUID

import entities.ReservationEntity

trait ReservationPersist {

  def findBySpaceId(spaceId: UUID): List[ReservationEntity]

  def findByClientId(clientId: UUID): List[ReservationEntity]

}
