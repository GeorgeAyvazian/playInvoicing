package models

import javax.persistence._
import javax.validation.constraints.NotNull

import play.api.libs.json._
import play.db.ebean.Model
import play.db.ebean.Model.Finder

import scala.annotation.meta.field

@Entity
@Table(name = "products")
case class Product(
                    @(Id@field)
                    @(GeneratedValue@field) id: Long = null.asInstanceOf[Long],
                    @(Column@field) @(NotNull@field) description: String = "",
                    @(Column@field)(name = "unitprice") unitPrice: Long = 0L,
                    @(ManyToOne@field)(cascade = Array(CascadeType.PERSIST, CascadeType.MERGE)) @(JoinColumn@field)(name = "tax_id", referencedColumnName = "id") @(NotNull@field) tax: TaxRate = new TaxRate()
                    )
  extends Model {}

object Product {
  implicit val writer = Json.writes[Product]
  implicit val reader = Json.reads[Product]
  private val finder = new Finder(classOf[Long], classOf[Product])

  def create(jsProduct: JsValue): Long = {
    val newProduct: Product = jsProduct.as[Product]
    newProduct save()
    newProduct.id
  }

  def findAll: JsArray = {
    import scala.collection.JavaConversions.collectionAsScalaIterable
    (Json arr collectionAsScalaIterable(finder all)).value.head.asInstanceOf[JsArray]
  }

  def find(id: Long) = Json toJson product(id)

  def product(id: Long) = finder byId id

  def delete(id: Long) = product(id) delete()
}
