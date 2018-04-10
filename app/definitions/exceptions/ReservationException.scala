package definitions.exceptions

trait ReservationException {

  object ReservationNotFoundException
    extends Exception("Reservation not found.")

}
