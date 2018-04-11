package facades

import java.util.UUID

import definitions.exceptions.AppException._
import models.Reservation
import persists.ReservationPersist

import scala.util.Try

class ReservationFacade(reservationPersist: ReservationPersist) extends BaseFacade {

  def find(id: UUID): Try[Reservation] = {
    Try(reservationPersist.find(id))
      .flatMap(_.toTry(ReservationNotFoundException))
      .map(Reservation.of)
  }

}