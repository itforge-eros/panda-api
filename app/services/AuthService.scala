package services

trait AuthService {

  def verify(username: String, password: String): Boolean

}
