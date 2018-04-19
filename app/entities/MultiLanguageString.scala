package entities

import henkan.convert.Syntax._
import models.inputs.MultiLanguageStringInput

case class MultiLanguageString(en: Option[String],
                               th: Option[String],
                               jp: Option[String])

object MultiLanguageString {

  def of(input: MultiLanguageStringInput): MultiLanguageString = input.to[MultiLanguageString]()

}
