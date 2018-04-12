package definitions.exceptions

import definitions.exceptions.AppException.SafeException

trait AuthorizationException extends HttpException {

  object WrongUsernameOrPasswordException
    extends Exception("Wrong username or password.")
      with SafeException

  object UnauthorizedException
    extends Exception("Unauthorized.")
      with SafeException

  object NoPermissionException
    extends Exception("You have no permission to access the resource.")
      with SafeException

  object MalformedJwtTokenException
    extends Exception("Malformed JWT token.")

  class JwtDecodingException(message: String)
    extends Exception(s"Cannot decode JWT. $message")
      with SafeException

  class WrongBearerHeaderFormatException(actual: String)
    extends BadRequestException(s"Wrong bearer header format. Expect: Bearer [token] Actual: $actual")
      with SafeException

}
