package services

import entities.ExistingMember

trait AuthenticationService {

  def login(username: String, password: String): Option[ExistingMember]

}
