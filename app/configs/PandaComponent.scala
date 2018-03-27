package configs

import com.softwaremill.macwire.wire
import configs.modules.{DatabaseModule, PandaModule}
import context.BaseContext
import controllers.GraphqlController
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator}
import router.Routes

import scala.concurrent.ExecutionContext

class PandaComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with PandaModule
  with DatabaseModule {

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit lazy val baseContext: BaseContext = wire[BaseContext]
  lazy val controller: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

  applicationEvolutions

}
