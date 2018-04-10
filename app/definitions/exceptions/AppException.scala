package definitions.exceptions

trait AppException extends GraphqlException
  with Authorizationexception
  with HttpException
  with SpaceException
  with MemberException
  with RequestException

object AppException extends AppException