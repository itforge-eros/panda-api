package controllers

import controllers.api.ApiController
import facades.AuthenticationFacade
import forms.LoginForm
import io.circe.generic.auto._
import models.MemberWithToken
import play.api.mvc.{BodyParser, ControllerComponents}

import scala.language.postfixOps

class AuthenticationController(cc: ControllerComponents,
                               override val authenticationFacade: AuthenticationFacade) extends ApiController(cc) {

  def login = Action(loginParser) { request =>
    authenticationFacade.login(request.body.username, request.body.password)
      .toResult
  }


  private val loginParser: BodyParser[LoginForm] = circe.json[LoginForm]

}