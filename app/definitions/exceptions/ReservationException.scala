package definitions.exceptions

import definitions.exceptions.AppException.SafeException

trait ReservationException {

  object ReservationNotFoundException
    extends Exception("Reservation not found.")
    with SafeException

}
