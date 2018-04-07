package definitions

import pdi.jwt.JwtAlgorithm

object AppSecurity {

  val key = "application-key"
  val algorithm = JwtAlgorithm.HS256

}
