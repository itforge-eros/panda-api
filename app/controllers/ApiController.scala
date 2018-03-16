package controllers

import play.api.mvc._

class ApiController(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok("Hello")
  }

}
