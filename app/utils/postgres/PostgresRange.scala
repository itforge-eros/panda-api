package utils.postgres

import org.postgresql.util._
import utils.StringUtil.splitWith
import utils.postgres.PostgresRange._

class PostgresRange(var start: Int, var end: Int) extends PGobject {

  setType("range")

  override def setValue(s: String): Unit = {
    val range = toRange(s) getOrElse postgresConversionException(s)
    start = range.start
    end = range.end
  }

  override def toString = s"[$start, $end)"


  private def postgresConversionException(s: String) = throw new PSQLException(GT.tr("Conversion to type {0} failed: {1}.", Array[AnyRef](`type`, s)), PSQLState.DATA_TYPE_MISMATCH)

}

object PostgresRange {

  def toRange(s: String): Option[Range] = {
    val pair = (toPair compose splitWith(",") compose removeBrackets compose removeWhiteSpace)(s)
    val a: Option[String] = pair map (_._1)
    val b: Option[String] = pair map (_._2)

    for {
      a1 <- a
      b1 <- b
    } yield Range(a1.toInt, b1.toInt)
  }


  private def removeBrackets: String => String = _.replaceAll("[()\\[\\]]", "")

  private def removeWhiteSpace: String => String = _.replaceAll("\\s+", "")

  private def toPair[A]: List[A] => Option[(A, A)] = {
    case a :: b :: Nil => Some(a, b)
    case _ => None
  }

}
