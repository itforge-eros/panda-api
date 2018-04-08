package spec

import java.time.Instant

import definitions.AppSecurity
import io.circe.Json
import io.circe.syntax._
import org.scalatest._
import org.scalatest.time.SpanSugar
import org.scalatestplus.play.FakeApplicationFactory
import pdi.jwt.{JwtCirce, JwtClaim}
import play.api.inject.DefaultApplicationLifecycle
import play.api.libs.json.JsValue
import play.api.{Application, ApplicationLoader, Configuration, Environment}
import play.core.DefaultWebCommands
import sangria.marshalling.MarshallingUtil._
import sangria.marshalling.circe.{CirceInputUnmarshaller, CirceResultMarshaller}
import sangria.marshalling.playJson._
import sangria.marshalling.{InputUnmarshaller, ResultMarshaller}
import spec.configs.MockApplicationLoader
import spec.data.MemberData
import spec.helpers.ControllerSpecHelper
import utils.graphql.GraphqlQuery

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

  implicit def CirceToScalaJson(circe: Json): JsValue = circe.convertMarshaled[JsValue]

  implicit val resultMarshaller: ResultMarshaller = CirceResultMarshaller

  implicit val inputUnmarshaller: InputUnmarshaller[Json] = CirceInputUnmarshaller

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

  private lazy val claim = JwtClaim(
    subject = MemberData.members.headOption map (_.id.toString),
    issuedAt = Some(Instant.now.getEpochSecond),
    expiration = Some(Instant.now().plusSeconds(31536000).getEpochSecond)
  )

  private lazy val token = JwtCirce.encode(claim, AppSecurity.key, AppSecurity.algorithm)

  implicit protected val headers: List[(String, String)] = List(("Authorization", s"Bearer $token"))

}
