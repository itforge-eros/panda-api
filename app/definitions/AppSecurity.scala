package definitions

import pdi.jwt.JwtAlgorithm
import pdi.jwt.algorithms.JwtHmacAlgorithm

object AppSecurity {

  val algorithm: JwtHmacAlgorithm = JwtAlgorithm.HS256

}
