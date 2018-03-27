package configs

import com.softwaremill.macwire.wire
import context.BaseContext
import controllers.GraphqlController
import persists.{MemberPersist, RequestPersist, SpacePersist}
import persists.postgres.{MemberPostgres, RequestPostgres, SpacePostgres}
import play.api.ApplicationLoader.Context
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.{DBComponents, Database, HikariCPComponents}
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator, NoHttpFiltersComponents}
import router.Routes
import schemas.SchemeDefinition.Mutation

import scala.concurrent.ExecutionContext

class PandaComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with PandaModule
  with DBComponents
  with HikariCPComponents
  with EvolutionsComponents
  with I18nComponents
  with NoHttpFiltersComponents {

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  lazy val spacePersist: SpacePersist = wire[SpacePostgres]
  lazy val requestPersist: RequestPersist = wire[RequestPostgres]
  lazy val memberPersist: MemberPersist = wire[MemberPostgres]
  lazy val mutation: Mutation = wire[Mutation]

  implicit lazy val baseContext: BaseContext = wire[BaseContext]
  lazy val database: Database = dbApi.database("default")
  lazy val controller: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

  applicationEvolutions

}
