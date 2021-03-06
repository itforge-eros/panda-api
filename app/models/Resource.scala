package models

import models.enums.Access

import scala.language.postfixOps

class Resource(val roles: List[Role]) {

  lazy val permissions: List[Permission] = roles flatMap (_.permissions) distinct
  lazy val accesses: List[Access] = permissions flatMap (_.accesses) distinct

}
