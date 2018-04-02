package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.ReservationEntity
import persists.ReservationPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class ReservationPostgres(db: Database) extends ReservationPersist
  with PostgresUtil {

  override def findBySpaceId(spaceId: UUID): List[ReservationEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM reservation WHERE space_id=$spaceId::uuid" as rowParser.*
  }

  override def findByClientId(clientId: UUID): List[ReservationEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE member_id=$clientId::uuid" as rowParser.*
  }

  private lazy val rowParser: RowParser[ReservationEntity] =
    Macro.namedParser[ReservationEntity](ColumnNaming.SnakeCase)

}
