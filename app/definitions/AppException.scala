package definitions

import java.io.{PrintWriter, StringWriter}

import com.typesafe.scalalogging.LazyLogging
import play.api.data.Form

object AppException extends LazyLogging {

  class GraphqlSyntaxError extends Exception("GraphQL syntax error.")

  case object TooComplexQueryError extends Exception("Query is too expensive.")

  class UnexpectedError(other: Throwable) extends Exception("Something went wrong.") {

    val stringWriter = new StringWriter
    other.printStackTrace(new PrintWriter(stringWriter))
    logger.error(stringWriter.toString)
  }

  case class FormException(message: String) extends Exception("Input error." + description(message)) {

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

