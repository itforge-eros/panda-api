package services

import entities.ExistingMember

import scala.util.Try

trait AuthenticationService {

  def login(username: String, password: String): Try[Option[ExistingMember]]

}
