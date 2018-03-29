package utils

import scala.util.Try

object RangeUtil {

  def parseRange(s: String): Option[Range] = (s split ",").toList match {
    case start :: end :: Nil => Try(Range(start.toInt, end.toInt)) toOption
    case _ => None
  }

}
