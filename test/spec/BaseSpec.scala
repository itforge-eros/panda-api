package spec

import configurations.CustomApplicationLoader
import org.scalatest._
import org.scalatest.time.SpanSugar
import org.scalatestplus.play.FakeApplicationFactory
import play.api.inject.DefaultApplicationLifecycle
import play.api.libs.json.{JsObject, JsString}
import play.api.{Application, ApplicationLoader, Configuration, Environment}
import play.core.DefaultWebCommands
import sangria.macros._

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

  override def fakeApplication(): Application = new CustomApplicationLoader().load(context)

  protected val query = "{spaces{id}}"

  protected val graphqlBody = JsObject(Seq(
    "query" -> JsString(query)
  ))

}
