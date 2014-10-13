package controllers

import java.lang.{Long => L}

import models.TaxRate
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import play.db.ebean.Model.Finder

object TaxController extends Controller {

  def all = Action {
    Ok(TaxRate findAll)
  }

  import play.api.libs.functional.syntax._

  implicit val reader : Reads[TaxRate]= (
    (__ \ 'id).readNullable[L] and
    (__ \ 'description).read[String] and
    (__ \ 'amount).read[Long])((i, d, a) => {
    TaxRate.apply(i.orNull, d, a)
  })

  implicit val writer = Json.writes[TaxRate]
  private val finder = new Finder(classOf[L], classOf[TaxRate])


  def find(term: String) = Action(parse.empty) { r =>
    Ok((Json arr i(finder.where().icontains("description", term).findList())).value.head)
  }

  def create() = Action(parse.json) { r =>
    val newTaxRate = r.body.as[TaxRate]
    newTaxRate save()
    Ok(Json toJson newTaxRate)
  }

  def delete(id: Long) = Action(parse.empty) { r =>
    TaxRate delete id
    Ok
  }

  def update() = Action(parse.json) { request =>
    TaxRate update request.body
    Ok
  }

}