package configs

import com.softwaremill.macwire.wire
import controllers.GraphqlController
import persists.SpacePersist
import persists.postgres.SpacePostgres
import play.api.ApplicationLoader.Context
import play.api.db.{DBComponents, Database, HikariCPComponents}
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator, NoHttpFiltersComponents}
import router.Routes

import scala.concurrent.ExecutionContext

class PandaComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with PandaModule
  with DBComponents
  with HikariCPComponents
  with I18nComponents
  with NoHttpFiltersComponents {

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  lazy val prefix: String = "/"
  lazy val database: Database = dbApi.database("default")
  lazy val persist: SpacePersist = wire[SpacePostgres]
  lazy val controller: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

}
