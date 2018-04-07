package config.components

import entities.ExistingMember
import services.AuthenticationService

trait ServiceComponents {

  lazy val authService: AuthenticationService = (_, _) => Some(ExistingMember(
    "59070009",
    "Kavin",
    "Ruengprateepsang",
    "email@email.com"
  ))

}
