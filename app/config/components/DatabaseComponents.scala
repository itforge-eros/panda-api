package config.components

import com.softwaremill.macwire.wire
import persists.postgres.{ReviewPostgres, MemberPostgres, RequestPostgres, SpacePostgres}
import persists.{ReviewPersist, MemberPersist, RequestPersist, SpacePersist}
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.{DBComponents, Database, HikariCPComponents}

trait DatabaseComponents extends DBComponents
  with HikariCPComponents
  with EvolutionsComponents {

  lazy val database: Database = dbApi.database("default")

  lazy val spacePersist: SpacePersist = wire[SpacePostgres]
  lazy val requestPersist: RequestPersist = wire[RequestPostgres]
  lazy val memberPersist: MemberPersist = wire[MemberPostgres]
  lazy val reviewPersist: ReviewPersist = wire[ReviewPostgres]

  applicationEvolutions

}
