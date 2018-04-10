package definitions.exceptions

trait Authorizationexception extends HttpException {

  object UnauthorizedException
    extends Exception("Unauthorized.")

  object WrongUsernameOrPasswordException
    extends Exception("Wrong username or password.")

  object MalformedJwtTokenException
    extends Exception("Malformed JWT token.")

  class AccessDeniedException(message: String)
    extends Exception(s"Access denied. $message")

  class JwtDecodingException(message: String)
    extends Exception(s"Cannot decode JWT. $message")

  class WrongBearerHeaderFormatException(actual: String)
    extends BadRequestException(s"Wrong bearer header format. Expect: Bearer [token] Actual: $actual")

}
