package modules

import com.softwaremill.macwire.wire
import controllers.ApiController
import play.api.i18n.Langs
import play.api.mvc.ControllerComponents

trait ApiModule  {

  lazy val apiController: ApiController = wire[ApiController]

  def langs: Langs

  def controllerComponents: ControllerComponents

}
