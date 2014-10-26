package controllers

import models._
import play.api.cache.Cache.{getAs, set}
import play.api.libs.json.Json.{arr, toJson}
import play.api.mvc.{Action, Controller}
import play.db.ebean.Model.Finder

object Taxes extends Controller {

  private lazy val finder = new Finder(classOf[L], classOf[TaxRate])

  def all = Action { Ok(arr(i(fromCache)).value head) }

  def find(term: String, fields: String) = Action(parse empty) { r =>
    Ok(arr(i(fromCache).filter(p =>
      (p.description.toLowerCase contains term.toLowerCase)
        || (term.isInstanceOf[Int] && p.amount == term.toInt)
    )).value head)
  }

  def create = Action(parse json) { r =>
    val tr = r.body.as[TaxRate]
    tr save() and refreshCache and Ok(toJson(tr))
  }

  def delete(id: Long) = Action(parse empty) { r => finder byId id delete() and refreshCache and Ok }

  def update = Action(parse json) { r => r.body.as[TaxRate] update() and refreshCache and Ok }

  private def refreshCache = {
    import play.api.Play.current
    val taxes = finder all()
    set("taxes", taxes) and taxes
  }

  private def fromCache = {
    import play.api.Play.current
    getAs[jList[TaxRate]]("taxes").getOrElse[jList[TaxRate]](refreshCache)
  }
}

object Products extends Controller {

  private lazy val finder = new Finder(classOf[L], classOf[Product])

  def all = Action { Ok(arr(i(fromCache)).value head) }

  def find(term: String, fields: String) = Action(parse empty) { r =>
    Ok(arr(i(fromCache).filter(p => p.description.toLowerCase contains term.toLowerCase)).value head)
  }

  def create = Action(parse json) { r =>
    val p = r.body.as[Product]
    refreshCache and p save() and Ok(toJson(p))
  }

  def delete(id: Long) = Action(parse empty) { r => finder byId id delete() and refreshCache and Ok }

  def update = Action(parse json) { r => r.body.as[Product] update() and refreshCache and Ok }

  private def refreshCache = {
    import play.api.Play.current
    val products = finder all()
    set("products", products) and products
  }

  private def fromCache = {
    import play.api.Play.current
    getAs[jList[Product]]("products").getOrElse[jList[Product]](refreshCache)
  }
}

object LineItems extends Controller {

  private lazy val finder = new Finder(classOf[L], classOf[LineItem])
  private lazy val CACHE_KEY = "lineItems"

  def all = Action { Ok(arr(i(fromCache)).value head) }

  def find(term: String, fields: String) = Action(parse empty) { r =>
    Ok(arr(i(fromCache).filter(li => true)).value head)
  }

  def create = Action(parse json) { r =>
    val p = r.body.as[LineItem]
    refreshCache and p save() and Ok(toJson(p))
  }

  def createEmpty = Action { Ok(toJson(new LineItem())) }

  def createEmptyInvoice = Action { Ok(toJson(new Invoice())) }

  def delete(id: Long) = Action(parse empty) { r => finder byId id delete() and refreshCache and Ok }

  def update = Action(parse json) { r => r.body.as[LineItem] update() and refreshCache and Ok }

  private def refreshCache = {
    import play.api.Play.current
    val lineItems = finder all()
    set(CACHE_KEY, lineItems) and lineItems
  }

  private def fromCache = {
    import play.api.Play.current
    getAs[jList[LineItem]](CACHE_KEY).getOrElse[jList[LineItem]](refreshCache)
  }
}
object Invoices extends Controller {

  private lazy val finder = new Finder(classOf[L], classOf[Invoice])
  private lazy val CACHE_KEY = "invoices"

  def all = Action { Ok(arr(i(fromCache)).value head) }

  def find(term: String, fields: String) = Action(parse empty) { r =>
    Ok(arr(i(fromCache).filter(li => true)).value head)
  }

  def create = Action(parse json) { r =>
    val p = r.body.as[Invoice]
    refreshCache and p save() and Ok(toJson(p))
  }

  def createEmpty = Action { Ok(toJson(new Invoice())) }

  def createEmptyInvoice = Action { Ok(toJson(new Invoice())) }

  def delete(id: Long) = Action(parse empty) { r => finder byId id delete() and refreshCache and Ok }

  def update = Action(parse json) { r => r.body.as[Invoice] update() and refreshCache and Ok }

  private def refreshCache = {
    import play.api.Play.current
    val invoices = finder all()
    set(CACHE_KEY, invoices) and invoices
  }

  private def fromCache = {
    import play.api.Play.current
    getAs[jList[Invoice]](CACHE_KEY).getOrElse[jList[Invoice]](refreshCache)
  }
}