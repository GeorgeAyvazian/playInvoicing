package controllers

import models.SomeModel
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

object SomeModelController extends Controller {

  def all() = Action {
    Ok(SomeModel.findAll)
  }

  def create() = Action(parse.json) { request =>
    Ok(Json.toJson(SomeModel.create(request.body)))
  }


  def delete(id: String) = Action(parse.empty) { request =>
    val l: Long = id.toLong
    SomeModel.delete(l)
    Ok(Json.toJson(l))
  }
}