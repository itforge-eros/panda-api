package spec

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCode}
import org.scalamock.scalatest.MockFactory

import scala.io.Source._

trait MockClientHelper
  extends MockFactory {

  protected def resource(path: String): String = {
    val resource = getClass.getResource(path)

    fromFile(resource.getFile).mkString
  }

  protected def responseMock(status: Int, body: String): HttpResponse = {
    HttpResponse(
      status = StatusCode.int2StatusCode(status)
      , headers = Nil
      , entity = HttpEntity(`application/json`, body))
  }

}
