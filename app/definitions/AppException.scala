package definitions

import definitions.exceptions._

object AppException extends GraphqlException
  with AuthenticationException
  with HttpException
  with SpaceException
  with MemberException
