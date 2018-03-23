package configs

import controllers.GraphqlController
import persists.SpacePersist
import play.api.i18n.Langs
import play.api.mvc.ControllerComponents
import play.api.routing.Router
import services.UuidService

trait PandaModule {

  def langs: Langs

  def controllerComponents: ControllerComponents

  val prefix: String

  val persist: SpacePersist

  val controller: GraphqlController

  val router: Router

}
