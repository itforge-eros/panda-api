package spec.helpers

import play.api.Application
import play.api.libs.json.{JsNull, JsValue, Json, Reads}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers.{route, _}

import scala.concurrent.Future

trait ControllerSpecHelper {

  val application: Application

  protected def request(method: String
                        , path: String
                        , json: JsValue = JsNull)
                       (implicit headers: List[(String, String)] = List()): Future[Result] = {
    route(
      application
      , FakeRequest(method, path)
        .withJsonBody(json)
        .withHeaders(headers:_*)
    ).get
  }

  protected def json[T](pairs: (String, T)*): JsValue = Json.parse(
    pairs.map {
      case (k, v) if isArray(v.toString) || isObject(v.toString) => s""""$k": $v"""
      case (k, v) => s""""$k": "$v""""
    }.mkString("{", ", ", "}")
  )

  protected def array[T](list: T*): JsValue = Json.parse(
    list.map {
      case l if isArray(l.toString) || isObject(l.toString) => s"""$l"""
      case l => s""""$l""""
    }.mkString("[", ", ", "]")
  )

  protected def result[T](response: Future[Result])
                         (implicit fjs: Reads[T]): Option[T] =
    (contentAsJson(response) \ "result").asOpt[T](fjs)

  private def isArray(str: String): Boolean =
    str.startsWith("[")

  private def isObject(str: String): Boolean =
    str.startsWith("{")

}
