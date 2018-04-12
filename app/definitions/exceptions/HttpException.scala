package definitions.exceptions

import java.io.{PrintWriter, StringWriter}

import com.typesafe.scalalogging.LazyLogging
import definitions.exceptions.AppException.SafeException
import play.api.data.Form

trait HttpException extends LazyLogging {

  class BadRequestException(message: String)
    extends Exception(message)
      with SafeException

  class UnexpectedError(other: Throwable)
    extends Exception("Something went wrong.") {

    val stringWriter = new StringWriter
    other.printStackTrace(new PrintWriter(stringWriter))
    logger.error(stringWriter.toString)
  }

  case class FormException(message: String)
    extends Exception("Input error." + description(message))
      with SafeException {

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
