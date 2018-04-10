package definitions.exceptions

trait AppException extends GraphqlException
  with AuthorizationException
  with HttpException
  with SpaceException
  with MemberException
  with RequestException

object AppException extends AppException
