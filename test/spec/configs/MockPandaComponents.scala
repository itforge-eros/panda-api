package spec.configs

import com.softwaremill.macwire.wire
import config.components.{BuiltInComponentsWithLogging, GraphqlComponents}
import controllers.{AssetsComponents, GraphqlController}
import facades.GraphqlFacade
import play.api.ApplicationLoader.Context
import play.api.NoHttpFiltersComponents
import play.api.i18n.I18nComponents
import play.api.routing.Router
import router.Routes
import schemas.PandaContext

class MockPandaComponents(context: Context) extends BuiltInComponentsWithLogging(context)
  with MockDatabaseComponents
  with GraphqlComponents
  with AssetsComponents
  with I18nComponents
  with NoHttpFiltersComponents {

  lazy val routePrefix: String = "/"
  lazy val pandaContext: PandaContext = wire[PandaContext]
  lazy val graphqlFacade: GraphqlFacade = wire[GraphqlFacade]
  lazy val graphqlController: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

}