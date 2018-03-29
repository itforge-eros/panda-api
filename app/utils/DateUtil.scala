package utils

import java.text.SimpleDateFormat
import java.util.Date

import scala.util.Try

object DateUtil {

  val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def parseDate(s: String): Option[Date] = Try(dateFormat.parse(s)) toOption

}
