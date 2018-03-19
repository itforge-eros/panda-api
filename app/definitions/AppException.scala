package definitions

import play.api.data.Form

object AppException {

  class GraphqlSyntaxError
    extends Exception("")

  case object TooComplexQueryError extends Exception("Query is too expensive.")

  case class FormException(message: String)
    extends Exception("input error." + description(message)) {

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

