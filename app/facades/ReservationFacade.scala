package facades

import java.util.UUID

import models.{BaseModel, Reservation}
import persists.ReservationPersist

import scala.util.Try

class ReservationFacade(reservationPersist: ReservationPersist) extends BaseFacade {

  def find(id: UUID): Try[Reservation] = {
    reservationPersist.find(id)
      .flatMap(_.toTry(ReservationNotFoundException))
      .map(Reservation.of)
  }

}
