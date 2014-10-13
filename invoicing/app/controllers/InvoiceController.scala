package controllers

import models.LineItem
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

object InvoiceController extends Controller {

  def newLineItem = Action {
    Ok(Json toJson new LineItem())
  }
}