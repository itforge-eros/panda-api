package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object ReservationException {

  object ReservationNotFoundException
    extends Exception("Reservation not found.")
      with SafeException

}
