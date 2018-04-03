package config.components

import services.AuthService

trait ServiceComponents {

  lazy val authService: AuthService = (_, _) => true

}
