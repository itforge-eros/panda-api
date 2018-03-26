package configs

import controllers.GraphqlController
import play.api.i18n.Langs
import play.api.mvc.ControllerComponents
import play.api.routing.Router

trait PandaModule {

  def langs: Langs

  def controllerComponents: ControllerComponents

  lazy val prefix: String = "/"

  val controller: GraphqlController

  val router: Router

}
