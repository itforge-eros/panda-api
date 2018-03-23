package spec.configs

import com.softwaremill.macwire.wire
import configs.PandaModule
import controllers.{AssetsComponents, GraphqlController}
import persists.SpacePersist
import play.api.ApplicationLoader.Context
import play.api.i18n.I18nComponents
import play.api.libs.concurrent.AkkaComponents
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator, NoHttpFiltersComponents}
import router.Routes
import services.UuidService
import spec.MockSpacePersist

import scala.concurrent.ExecutionContext

class MockComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with PandaModule
  with AssetsComponents
  with I18nComponents
  with NoHttpFiltersComponents {

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  lazy val prefix: String = "/"

  lazy val persist: SpacePersist = new MockSpacePersist

  lazy val controller: GraphqlController = wire[GraphqlController]

  lazy val router: Router = wire[Routes]

}
