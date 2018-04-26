package config.components

import com.softwaremill.macwire.wire
import controllers.{AssetsComponents, GraphqlController}
import facades._
import models.{Identity, Member}
import play.api.ApplicationLoader.Context
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator, NoHttpFiltersComponents}
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.filters.cors.{CORSComponents, CORSFilter}
import router.Routes
import schema.PandaContext

class PandaComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with DatabaseComponents
  with ServiceComponents
  with GraphqlComponents
  with AssetsComponents
  with I18nComponents
  with CORSComponents {

  lazy val routePrefix: String = "/"
  lazy val pandaContext: Option[Identity] => PandaContext = (_: Option[Identity]) => wire[PandaContext]

  lazy val authenticationFacade: AuthenticationFacade = wire[AuthenticationFacade]
  lazy val authorizationFacade: AuthorizationFacade = wire[AuthorizationFacade]
  lazy val graphqlFacade: GraphqlFacade = wire[GraphqlFacade]
  lazy val spaceFacade: SpaceFacade = wire[SpaceFacade]
  lazy val memberFacade: MemberFacade = wire[MemberFacade]
  lazy val requestFacade: RequestFacade = wire[RequestFacade]
  lazy val reviewFacade: ReviewFacade = wire[ReviewFacade]
  lazy val reservationFacade: ReservationFacade = wire[ReservationFacade]
  lazy val departmentFacade: DepartmentFacade = wire[DepartmentFacade]
  lazy val roleFacade: RoleFacade = wire[RoleFacade]
  lazy val permissionFacade: PermissionFacade = wire[PermissionFacade]
  lazy val materialFacade: MaterialFacade = wire[MaterialFacade]
  lazy val problemFacade: ProblemFacade = wire[ProblemFacade]
  lazy val imageUploadFacade: ImageUploadFacade = wire[ImageUploadFacade]

  lazy val graphqlController: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

  override val httpFilters = Seq(corsFilter)

  LoggerConfigurator(context.environment.classLoader) foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

}
