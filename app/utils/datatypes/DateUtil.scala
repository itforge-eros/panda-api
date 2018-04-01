package utils.datatypes

import java.text.SimpleDateFormat
import java.util.Date

import scala.language.postfixOps
import scala.util.Try

object DateUtil {

  val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def parseDate(s: String): Option[Date] = Try(dateFormat.parse(s)) toOption

}
