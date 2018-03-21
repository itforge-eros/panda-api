package configurations

import play.api.ApplicationLoader.Context
import play.api._

class PandaApplicationLoader extends ApplicationLoader {

  def load(context: Context): Application = new PandaComponent(context).application

}
