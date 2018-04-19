package facades

import java.util.UUID

import definitions.exceptions.ReservationException.ReservationNotFoundException
import models.Reservation
import persists.ReservationPersist

import scala.util.Try

class ReservationFacade(reservationPersist: ReservationPersist) extends BaseFacade {

  def find(id: UUID): Try[Reservation] = validateWith() {
    reservationPersist.find(id) toTry ReservationNotFoundException map Reservation.of
  }

}
