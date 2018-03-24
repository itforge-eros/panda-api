package spec.configs

import com.softwaremill.macwire.wire
import configs.PandaModule
import context.BaseContext
import controllers.{AssetsComponents, GraphqlController}
import play.api.ApplicationLoader.Context
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator, NoHttpFiltersComponents}
import router.Routes
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

  implicit lazy val baseContext: BaseContext = BaseContext(wire[MockSpacePersist])

  lazy val prefix: String = "/"

  lazy val controller: GraphqlController = wire[GraphqlController]

  lazy val router: Router = wire[Routes]

}
