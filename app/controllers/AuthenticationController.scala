package controllers

import controllers.api.ApiController
import facades.AuthenticationFacade
import forms.LoginForm
import io.circe.generic.auto._
import models.MemberWithToken
import play.api.mvc.{BodyParser, ControllerComponents}

import scala.language.postfixOps

class AuthenticationController(cc: ControllerComponents,
                               authFacade: AuthenticationFacade) extends ApiController(cc) {

  def login = Action(loginParser) { request =>
    authFacade.login(request.body.username, request.body.password)
      .map(MemberWithToken.tupled)
      .toResult
  }


  private val loginParser: BodyParser[LoginForm] = circe.json[LoginForm]

  override protected val authentication = authFacade

}
