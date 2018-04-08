package config.components

import services.{AuthenticationService, MockAuthenticationService}

trait ServiceComponents {

  lazy val authenticationService: AuthenticationService = new MockAuthenticationService

}
