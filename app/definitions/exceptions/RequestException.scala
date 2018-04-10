package definitions.exceptions

trait RequestException {

  object RequestNotFoundException
    extends Exception("Request not found.")

  object CannotCreateRequestException
    extends Exception("Cannot create request.")

}
