package forms

import play.api.data.Forms._
import play.api.data.format.Formatter
import play.api.data.{Form, FormError}
import play.api.libs.json._
import utils.GraphqlUtil.parseVariables

case class GraphqlForm(query: String, operation: Option[String], variables: Option[JsObject])

object GraphqlForm {

  implicit object JsonFormatter extends Formatter[JsObject] {

    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], JsObject] = {
      data.get(key) map parseVariables match {
        case Some(value) => Right(value)
        case None => Left(Seq(FormError("Form error: ", key, Nil)))
      }
    }

    override def unbind(key: String, value: JsObject): Map[String, String] = Map(key -> value.toString())

  }

  val form: Form[GraphqlForm] = Form(mapping(
    "query" -> text,
    "operationName" -> optional(text),
    "variables" -> optional(of[JsObject])
  )(GraphqlForm.apply)(GraphqlForm.unapply))

}
