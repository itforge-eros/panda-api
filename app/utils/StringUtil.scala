package utils

object StringUtil {

  def splitWith(delimiter: String)(s: String): List[String] = s split delimiter toList

  implicit class StringExtension(s: String) {

    def splitWith(delimiter: String) = s split delimiter toList

  }

}
