package persists.postgres

import anorm.Macro.ColumnNaming
import anorm._
import entities.SpaceEntity
import persists.SearchPersist
import play.api.db.Database

class SearchPostgres(db: Database) extends SearchPersist {

  override def space(query: String,
                     department: Option[String],
                     tags: List[String],
                     capacity: Option[Int]): List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"""
        SELECT
          spaces.id,
          spaces.name,
          spaces.full_name,
          spaces.description,
          spaces.tags,
          spaces.capacity,
          spaces.is_available,
          spaces.created_at,
          spaces.updated_at,
          spaces.department_id,
         	ts_rank(document, to_tsquery($query)) + fuzzy_score / 40 + spaces.available_duration/1000000 AS total_score
        FROM (
         	SELECT
         		to_tsvector(space.name) ||
         		to_tsvector(space.full_name) ||
         		to_tsvector(array_to_string(space.tags, ' ')) ||
         		to_tsvector(department.name) ||
         		to_tsvector(department.full_english_name) ||
         		to_tsvector(department.full_thai_name) AS document,
         		similarity(space.name, $query) AS fuzzy_score,
            space.id,
            space.name,
            space.full_name,
            space.description,
            space.tags,
            space.capacity,
            space.is_available,
            space.created_at,
            space.updated_at,
            space.department_id,
            coalesce((
              SELECT 24 - (sum(upper(period) - lower(period)))
              FROM reservation
              WHERE space_id = space.id
              GROUP BY space_id
            ), 24) AS available_duration
         	FROM space
         	JOIN department ON department.id = space.department_id
         	WHERE space.capacity >= ${capacity.getOrElse(0): Int}
          AND CASE WHEN ${department.isDefined}
            THEN department.name = ${department.getOrElse(""): String}
            ELSE true END
         	AND ARRAY[$tags]::varchar[] <@ space.tags
         	AND space.is_available = true
        ) AS spaces
        WHERE ts_rank(document, to_tsquery($query)) + fuzzy_score / 40 + spaces.available_duration/1000000 > 0
        ORDER BY total_score DESC;
       """ as rowParser.*
  }


  private lazy val rowParser =
    Macro.namedParser[SpaceEntity](ColumnNaming.SnakeCase)

}
