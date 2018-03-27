package configs.modules

import controllers.{AssetsComponents, GraphqlController}
import play.api.NoHttpFiltersComponents
import play.api.i18n.{I18nComponents, Langs}
import play.api.mvc.ControllerComponents
import play.api.routing.Router

trait PandaModule extends GraphqlModule
  with AssetsComponents
  with I18nComponents
  with NoHttpFiltersComponents{

  def langs: Langs

  def controllerComponents: ControllerComponents

  lazy val prefix: String = "/"

  val controller: GraphqlController

  val router: Router

}
