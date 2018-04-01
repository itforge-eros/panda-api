package utils.datatypes

object StringUtil {

  def splitWith(delimiter: String)(s: String): List[String] = s split delimiter toList

}
