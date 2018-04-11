package services

import entities.ExistingMemberEntity

import scala.util.Try

trait AuthenticationService {

  def login(username: String, password: String): Try[Option[ExistingMemberEntity]]

}
