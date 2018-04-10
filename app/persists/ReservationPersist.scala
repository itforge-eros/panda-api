package persists

import java.util.UUID

import entities.ReservationEntity

import scala.util.Try

trait ReservationPersist {

  def find(id: UUID): Try[Option[ReservationEntity]]

  def findBySpaceId(spaceId: UUID): List[ReservationEntity]

  def findByClientId(clientId: UUID): List[ReservationEntity]

}
