package models

import java.lang.{Long => L}
import javax.persistence._
import javax.validation.constraints.NotNull

import play.api.libs.json._
import play.db.ebean.Model
import play.db.ebean.Model.Finder

import scala.annotation.meta.field

@Entity
@Table(name = "tax_rates")
case class TaxRate(
                    @(Id@field)
                    @(GeneratedValue@field) id: L = null,
                    @(Column@field) description: String = "",
                    @(Column@field) @(NotNull@field) amount: Long = 0L
                    )
  extends Model {}

object TaxRate {


  import play.api.libs.functional.syntax._

  implicit val idlessReader : Reads[TaxRate]= ((__ \ 'id).readNullable[L] and
    (__ \ 'description).read[String] and
    (__ \ 'amount).read[Long])((i, d, a) => {
    TaxRate.apply(i.orNull, d, a)
  })

  implicit val writer = Json.writes[TaxRate]
  private val finder = new Finder(classOf[L], classOf[TaxRate])

  def find(term: String) = (Json arr i(finder.where().icontains("description", term).findList())).value.head

  def taxRate(id: L) = finder byId id

  def findAll = (Json arr i(finder all)).value.head.asInstanceOf[JsArray]

  def save(jsTaxRate: JsValue) = {
    val newTaxRate: TaxRate = jsTaxRate.as(TaxRate.idlessReader)
    newTaxRate save()
    Json toJson newTaxRate.id
  }

  def update(jsTaxRate: JsValue) = jsTaxRate.as(TaxRate.idlessReader) update()


  def delete(id: L) = taxRate(id) delete()
}