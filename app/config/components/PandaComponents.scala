package config.components

import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, GraphqlController}
import facades._
import models.Member
import play.api.ApplicationLoader.Context
import play.api.NoHttpFiltersComponents
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.filters.cors.{CORSComponents, CORSFilter}
import router.Routes
import schemas.PandaContext

class PandaComponents(context: Context) extends BuiltInComponentsWithLogging(context)
  with DatabaseComponents
  with ServiceComponents
  with GraphqlComponents
  with AssetsComponents
  with I18nComponents
  with CORSComponents {

  lazy val routePrefix: String = "/"
  lazy val pandaContext: Option[Member] => PandaContext = (_: Option[Member]) => wire[PandaContext]

  lazy val authenticationFacade: AuthenticationFacade = wire[AuthenticationFacade]
  lazy val graphqlFacade: GraphqlFacade = wire[GraphqlFacade]
  lazy val spaceFacade: SpaceFacade = wire[SpaceFacade]
  lazy val memberFacade: MemberFacade = wire[MemberFacade]
  lazy val requestFacade: RequestFacade = wire[RequestFacade]
  lazy val reviewFacade: ReviewFacade = wire[ReviewFacade]
  lazy val reservationFacade: ReservationFacade = wire[ReservationFacade]
  lazy val groupFacade: GroupFacade = wire[GroupFacade]

  lazy val graphqlController: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

  override val httpFilters = Seq(corsFilter)

}
