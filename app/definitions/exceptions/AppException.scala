package definitions.exceptions

protected trait AppException extends GraphqlException
  with AuthorizationException
  with HttpException
  with SpaceException
  with MemberException
  with RequestException
  with ReservationException

object AppException extends AppException {

  trait SafeException

}
