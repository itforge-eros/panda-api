package modules

import _root_.controllers.AssetsComponents
import com.softwaremill.macwire.wire
import play.api.ApplicationLoader.Context
import play.api._
import play.api.i18n.I18nComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import router.Routes

class CustomApplicationLoader extends ApplicationLoader {

  def load(context: Context): Application = new CustomComponent(context).application

}

class CustomComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with ApiModule
  with AssetsComponents
  with I18nComponents
  with HttpFiltersComponents {

  lazy val prefix: String = "/"
  lazy val router: Router = wire[Routes]

}
