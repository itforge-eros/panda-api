package spec

import io.circe.Json
import io.circe.syntax._
import models.GraphqlQuery
import org.scalatest._
import org.scalatest.time.SpanSugar
import org.scalatestplus.play.FakeApplicationFactory
import play.api.inject.DefaultApplicationLifecycle
import play.api.libs.json.{JsValue, Json => ScalaJson}
import play.api.{Application, ApplicationLoader, Configuration, Environment}
import play.core.DefaultWebCommands
import spec.configs.MockApplicationLoader
import spec.helpers.ControllerSpecHelper

import scala.language.{implicitConversions, postfixOps}

abstract class BaseSpec extends AsyncWordSpec
  with FakeApplicationFactory
  with Matchers
  with CustomMatcher
  with OptionValues
  with TryValues
  with PrivateMethodTester
  with ControllerSpecHelper
  with SpanSugar {

  private lazy val environment = Environment.simple()

  private lazy val context = ApplicationLoader.Context(
    environment = environment,
    sourceMapper = None,
    webCommands = new DefaultWebCommands(),
    initialConfiguration = Configuration.load(environment),
    lifecycle = new DefaultApplicationLifecycle()
  )

  override implicit lazy val application: Application = fakeApplication()

  override def fakeApplication(): Application = new MockApplicationLoader().load(context)

  implicit def CirceToScalaJson(circe: Json): JsValue = ScalaJson.parse(circe.noSpaces)

  protected val query: GraphqlQuery = GraphqlQuery(
    query = """
      | query findSpace($id: String!) {
      |   space(id: $id) {
      |     id
      |   }
      | }
    """.stripMargin,
    operationName = Some("findSpace"),
    variables = Some(Map("id" -> "001") asJson)
  )

}
