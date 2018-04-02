package spec.mockpersists

import java.util.UUID

import persists.ReservationPersist

class MockReservationPersist extends ReservationPersist {

  override def findBySpaceId(spaceId: UUID) = ???

  override def findByClientId(clientId: UUID) = ???

}
