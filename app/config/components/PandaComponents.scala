package config.components

import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, GraphqlController}
import facades.{AuthFacade, GraphqlFacade}
import play.api.ApplicationLoader.Context
import play.api.NoHttpFiltersComponents
import play.api.i18n.I18nComponents
import play.api.routing.Router
import router.Routes
import schemas.PandaContext

class PandaComponents(context: Context) extends BuiltInComponentsWithLogging(context)
  with DatabaseComponents
  with ServiceComponents
  with GraphqlComponents
  with AssetsComponents
  with I18nComponents
  with NoHttpFiltersComponents {

  lazy val routePrefix: String = "/"
  lazy val pandaContext: PandaContext = wire[PandaContext]
  lazy val authFacade: AuthFacade = wire[AuthFacade]
  lazy val graphqlFacade: GraphqlFacade = wire[GraphqlFacade]
  lazy val graphqlController: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

}
