package persists.postgres

import java.sql.Connection

import play.api.db.Database
import utils.Validation

class BasePostgres(db: Database) extends Validation {

  def execute[A](block: Connection => A): A = db.withConnection(block)

}
