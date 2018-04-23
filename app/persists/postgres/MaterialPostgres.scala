package persists.postgres

import java.sql.{PreparedStatement, Types}
import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.{MaterialEntity, MultiLanguageString}
import io.circe.generic.semiauto._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, _}
import persists.MaterialPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class MaterialPostgres(db: Database) extends MaterialPersist
  with PostgresUtil {

  override def find(id: UUID): Option[MaterialEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM material WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def findByDepartmentId(departmentId: UUID): List[MaterialEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM material WHERE department_id=$departmentId::uuid" as rowParser.*
  }

  override def create(entity: MaterialEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO material VALUES (
          ${entity.id}::uuid,
          ${entity.name},
          ${entity.createdAt},
          ${entity.departmentId}::uuid
        )
       """ executeStatement()
  }

  override def delete(id: UUID): Boolean = db.withConnection { implicit connection =>
    SQL"DELETE FROM material WHERE id = $id::uuid"executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[MaterialEntity](ColumnNaming.SnakeCase)

  implicit val columnToMaterialEntity: Column[MultiLanguageString] = anorm.Column.nonNull[MultiLanguageString] { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case json: org.postgresql.util.PGobject => {
        val result = decode[MultiLanguageString](json.getValue)
        result.fold(
          errors => Left(TypeDoesNotMatch(s"Cannot convert $value: ${value.asInstanceOf[AnyRef].getClass} to Json for column $qualified")),
          valid => Right(valid)
        )
      }
      case _ => Left(TypeDoesNotMatch(s"Cannot convert $value: ${value.asInstanceOf[AnyRef].getClass} to Json for column $qualified"))
    }
  }

  implicit val MultiLanguageStringToStatement: ToStatement[MultiLanguageString] {
    def set(s: PreparedStatement, i: Int, myClass: MultiLanguageString): Unit
  } = new ToStatement[MultiLanguageString] {
    def set(s: PreparedStatement, i: Int, myClass: MultiLanguageString): Unit = {
      val jsonObject = new org.postgresql.util.PGobject()
      jsonObject.setType("json")
      jsonObject.setValue(myClass.asJson.toString())
      s.setObject(i, jsonObject)
    }
  }

  implicit object MultiLanguageStringMetaData extends ParameterMetaData[MultiLanguageString] {
    val sqlType = "OTHER"
    val jdbcType = Types.OTHER
  }

  implicit val fooDecoder: Decoder[MultiLanguageString] = deriveDecoder
  implicit val fooEncoder: Encoder[MultiLanguageString] = deriveEncoder

}
