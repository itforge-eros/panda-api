package config.components

import entities.ExistingMember
import services.AuthService

trait ServiceComponents {

  lazy val authService: AuthService = (_, _) => Some(ExistingMember(
    "59070009",
    "Kavin",
    "Ruengprateepsang",
    "email@email.com"
  ))

}
