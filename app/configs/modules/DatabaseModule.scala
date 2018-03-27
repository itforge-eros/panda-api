package configs.modules

import com.softwaremill.macwire.wire
import persists.{MemberPersist, RequestPersist, SpacePersist}
import persists.postgres.{MemberPostgres, RequestPostgres, SpacePostgres}
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.{DBComponents, Database, HikariCPComponents}

trait DatabaseModule extends DBComponents
  with HikariCPComponents
  with EvolutionsComponents {

  lazy val database: Database = dbApi.database("default")

  lazy val spacePersist: SpacePersist = wire[SpacePostgres]
  lazy val requestPersist: RequestPersist = wire[RequestPostgres]
  lazy val memberPersist: MemberPersist = wire[MemberPostgres]

}