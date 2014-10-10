package controllers

import models.TaxRate
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

object TaxController extends Controller {

  def all = Action {
    Ok(TaxRate findAll)
  }

  def find(id: String) = Action(parse.empty) { request =>
    Ok(TaxRate find id)
  }

  def create() = Action(parse.json) { request =>
    Ok(Json toJson (TaxRate create request.body))
  }

  def delete(id: String) = Action(parse.empty) { request =>
    Ok(Json toJson (TaxRate delete id.toLong))
  }

  def update() = Action(parse.json) { request =>
    Ok(Json toJson (TaxRate update request.body))
  }

}