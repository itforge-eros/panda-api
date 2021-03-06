package config.components

import com.softwaremill.macwire.wire
import persists.postgres._
import persists._
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.{DBComponents, Database, HikariCPComponents}

trait DatabaseComponents extends DBComponents
  with HikariCPComponents
  with EvolutionsComponents {

  lazy val database: Database = dbApi.database("default")

  lazy val spacePersist: SpacePersist = wire[SpacePostgres]
  lazy val memberPersist: MemberPersist = wire[MemberPostgres]
  lazy val requestPersist: RequestPersist = wire[RequestPostgres]
  lazy val reviewPersist: ReviewPersist = wire[ReviewPostgres]
  lazy val reservationPersist: ReservationPersist = wire[ReservationPostgres]
  lazy val departmentPersist: DepartmentPersist = wire[DepartmentPostgres]
  lazy val rolePersist: RolePersist = wire[RolePostgres]
  lazy val memberRolePersist: MemberRolePersist = wire[MemberRolePostgres]
  lazy val materialPersist: MaterialPersist = wire[MaterialPostgres]
  lazy val problemPersist: ProblemPersist = wire[ProblemPostgres]
  lazy val authenticationPersist: AuthenticationPersist = wire[AuthenticationPostgres]
  lazy val searchPersist: SearchPersist = wire[SearchPostgres]

  applicationEvolutions

}
