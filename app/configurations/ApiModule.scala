package configurations

import com.softwaremill.macwire.wire
import controllers.GraphqlController
import play.api.i18n.Langs
import play.api.mvc.ControllerComponents

import scala.concurrent.ExecutionContext

trait ApiModule  {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  lazy val apiController: GraphqlController = wire[GraphqlController]

  def langs: Langs

  def controllerComponents: ControllerComponents

}
