package spec.configs

import play.api.ApplicationLoader.Context
import play.api._

class MockApplicationLoader extends ApplicationLoader {

  def load(context: Context): Application = new MockPandaComponents(context).application

}
