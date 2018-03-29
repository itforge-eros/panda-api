package utils

import java.text.SimpleDateFormat

import definitions.Violations.InvalidDateViolation

import scala.util.{Failure, Success, Try}

object DateUtil {

  val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  def parseDate(s: String) = Try(dateFormat.parse(s)) match {
    case Success(d) ⇒ Right(d)
    case Failure(_) ⇒ Left(InvalidDateViolation)
  }

}
