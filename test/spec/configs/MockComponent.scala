package spec.configs

import com.softwaremill.macwire.wire
import configs.modules.PandaModule
import controllers.GraphqlController
import persists.{MemberPersist, RequestPersist, SpacePersist}
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator}
import router.Routes
import schemas.BaseContext
import spec.persists.{MockMemberPersist, MockRequestPersist, MockSpacePersist}

import scala.concurrent.ExecutionContext

class MockComponent(context: Context) extends BuiltInComponentsFromContext(context)
  with PandaModule
  with MockDatabaseModule {

  implicit lazy val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit lazy val baseContext: BaseContext = wire[BaseContext]
  lazy val controller: GraphqlController = wire[GraphqlController]
  lazy val router: Router = wire[Routes]

}
