package definitions

import java.io.{PrintWriter, StringWriter}

import com.typesafe.scalalogging.LazyLogging
import play.api.data.Form

object AppException extends LazyLogging {

  class GraphqlSyntaxError
    extends Exception("GraphQL syntax error.")

  object GraphqlVariablesParseError
    extends Exception("Cannot parse GraphQL variables.")

  object TooComplexQueryError
    extends Exception("Query is too expensive.")

  object UnauthorizedException
    extends Exception("Unauthorized.")

  object WrongUsernameOrPasswordException
    extends Exception("Wrong username or password.")

  class JwtDecodingException(message: String)
   extends Exception(s"Cannot decode JWT. $message")

  class WrongBearerHeaderFormatException(actual: String)
    extends BadRequestException(s"Wrong bearer header format. Expect: Bearer [token] Actual: $actual")

  class BadRequestException(message: String)
    extends Exception(message)

  object MemberFirstLoginException
    extends Exception("First login.")

  object MemberNotFoundException
    extends Exception("Member not found exception.")

  object WrongUuidFormatException
    extends Exception("Wrong UUID format.")

  object MalformedJwtTokenException
    extends Exception("Malformed JWT token.")

  class UnexpectedError(other: Throwable)
    extends Exception("Something went wrong.") {

    val stringWriter = new StringWriter
    other.printStackTrace(new PrintWriter(stringWriter))
    logger.error(stringWriter.toString)
  }

  case class FormException(message: String)
    extends Exception("Input error." + description(message)) {

    def this(form: Form[_]) = {
      this(
        form.errors.map { error => {
          error.key + " " +
            (error.messages zipAll (error.args, "", "") map {
              case (msg, "") => s"$msg"
              case (msg, arg) => s"$msg=$arg"
            } mkString ", ").replace("error.", "")
        } }.mkString(", ")
      )
    }

  }


  private def description(message: String): String =
    if (message.isEmpty) "" else " " + message

}

