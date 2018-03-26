package utils

import java.sql.Timestamp

object Time {

  def now(): Timestamp = new Timestamp(System.currentTimeMillis())

}
