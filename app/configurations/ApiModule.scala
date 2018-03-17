package configurations

import com.softwaremill.macwire.wire
import controllers.GraphqlController
import play.api.i18n.Langs
import play.api.mvc.ControllerComponents

trait ApiModule  {

  lazy val apiController: GraphqlController = wire[GraphqlController]

  def langs: Langs

  def controllerComponents: ControllerComponents

}
