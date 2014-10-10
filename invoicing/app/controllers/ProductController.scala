package controllers

import models.Product
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

object ProductController extends Controller {

  def all() = Action {
    Ok(Product.findAll)
  }

  def create() = Action(parse.json) { request => Ok(Json toJson Product.create(request.body))}


  def delete(id: String) = Action(parse.empty) { request =>
    val productId: Long = id.toLong
    Product delete productId
    Ok(Json toJson productId)
  }
}