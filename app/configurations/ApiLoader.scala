package configurations

import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, GraphqlController}
import play.api.ApplicationLoader.Context
import play.api._
import play.api.db.{BoneCPComponents, DBComponents, Database}
import play.api.i18n.{I18nComponents, Langs}
import play.api.mvc.ControllerComponents
import play.api.routing.Router
import router.Routes

import scala.concurrent.ExecutionContext

class ApiLoader extends ApplicationLoader {

  def load(context: Context): Application = new ApiComponent(context).application

}

trait ApiModule {

  def langs: Langs

  def controllerComponents: ControllerComponents

}

class ApiComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with ApiModule
  with AssetsComponents
  with DBComponents
  with BoneCPComponents
  with I18nComponents
  with NoHttpFiltersComponents {

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  lazy val db: Database = dbApi.database("default")
  lazy val prefix: String = "/"
  lazy val graphqlController: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

}
