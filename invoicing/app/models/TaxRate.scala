package models

import javax.persistence.{Column, Entity, GeneratedValue, Id, Table}
import javax.validation.constraints.NotNull

import play.api.libs.json._
import play.db.ebean.Model
import play.db.ebean.Model.Finder

import scala.annotation.meta.field

@Entity
@Table(name = "tax_rates")
case class TaxRate(@(Id@field)
                   @(GeneratedValue@field) id: Long,
                   @(Column@field) description: String = "",
                   @(Column@field) @(NotNull@field) amount: Long = -1L
                    )
  extends Model {}

object TaxRate {

  implicit val reader = Json.reads[TaxRate]
  implicit val writer = Json.writes[TaxRate]
  private val finder = new Finder(classOf[Long], classOf[TaxRate])

  def find(term: String) = {
    import scala.collection.JavaConversions.collectionAsScalaIterable
    (Json arr collectionAsScalaIterable(finder.where().ilike("description", "%" + term + "%").findList())).value.head.asInstanceOf[JsArray]
  }

  def taxRate(id: Long) = finder byId id

  def findAll: JsArray = {
    import scala.collection.JavaConversions.collectionAsScalaIterable
    (Json arr collectionAsScalaIterable(finder all)).value.head.asInstanceOf[JsArray]
  }

  def create(jsTaxRate: JsValue) = {
    val newTaxRate: TaxRate = new TaxRate(null.asInstanceOf[Long], (jsTaxRate \ "description").as[String], (jsTaxRate \ "amount").as[Long])
    newTaxRate save()
    Json toJson newTaxRate
  }

  def update(jsTaxRate: JsValue) = {
    val newTaxRate: TaxRate = new TaxRate((jsTaxRate \ "id").as[Long], (jsTaxRate \ "description").as[String], (jsTaxRate \ "amount").as[Long])
    newTaxRate update()
    Json toJson newTaxRate
  }


  def delete(id: Long) = {
    val tax: TaxRate = taxRate(id)
    tax delete()
    tax
  }
}