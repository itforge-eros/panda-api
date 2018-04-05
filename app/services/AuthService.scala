package services

import entities.ExistingMember

trait AuthService {

  def login(username: String, password: String): Option[ExistingMember]

}
