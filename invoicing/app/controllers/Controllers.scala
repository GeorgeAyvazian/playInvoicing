package controllers


import com.avaje.ebean.ExpressionList
import models._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import play.db.ebean.Model.Finder

object Taxes extends Controller {

  private val finder = new Finder(classOf[L], classOf[TaxRate])

  def all = Action { Ok((Json arr i(finder all)).value.head) }

  def find(term: String, fields: String) = Action(parse.empty) { r =>
    val where: ExpressionList[TaxRate] = finder.where()
    fields.split(",").foreach(where.icontains(_, term))
    Ok((Json arr i(where findList())).value.head)
  }

  def create() = Action(parse.json) { r =>
    val tr = r.body.as[TaxRate]
    tr save() and Ok(Json toJson tr)
  }

  def delete(id: Long) = Action(parse.empty) { r => finder byId id delete() and Ok }

  def update() = Action(parse.json) { r => r.body.as[TaxRate] update() and Ok }

}

object Products extends Controller {

  private val finder = new Finder(classOf[L], classOf[Product])

  def all = Action { Ok((Json arr i(finder all)).value.head) }

  def find(term: String, fields: String*) = Action(parse.empty) { r =>
    val where: ExpressionList[Product] = finder.where()
    fields.foreach(where.icontains(_, term))
    Ok((Json arr i(where findList())).value.head)
  }

  def create() = Action(parse.json) { r =>
    val p = r.body.as[Product]
    p save() and Ok(Json toJson p)
  }

  def delete(id: Long) = Action(parse.empty) { r => finder byId id delete() and Ok }

  def update() = Action(parse.json) { r => r.body.as[Product] update() and Ok }

}