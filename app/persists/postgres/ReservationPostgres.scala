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

  override def find(id: UUID): Option[ReservationEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM reservation WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findBySpaceId(spaceId: UUID): List[ReservationEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM reservation WHERE space_id=$spaceId::uuid" as rowParser.*
  }

  override def findByClientId(clientId: UUID): List[ReservationEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE member_id=$clientId::uuid" as rowParser.*
  }

  override def insert(reservation: ReservationEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO reservation VALUES (
           ${reservation.id}::uuid,
           ${reservation.date},
           ${reservation.period}::int4range,
           ${reservation.isAttended},
           ${reservation.spaceId}::uuid,
           ${reservation.clientId}::uuid
         )
       """ executeStatement()
  }

  private lazy val rowParser =
    Macro.namedParser[ReservationEntity](ColumnNaming.SnakeCase)

}
