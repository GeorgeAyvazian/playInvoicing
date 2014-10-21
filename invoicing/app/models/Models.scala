package models

import java.util
import javax.persistence._
import javax.validation.constraints.NotNull

import play.api.libs.json.Json.{reads, writes}
import play.api.libs.json._
import play.db.ebean.Model

import scala.annotation.meta.field

@Entity
@Table(name = "users")
case class User(@(Id@field)
                @(GeneratedValue@field)
                (strategy = GenerationType.IDENTITY)
                id: L = null,
                @(NotNull@field)
                email: String,
                @(NotNull@field)
                password: String) extends Model {}

object User {
  implicit val reader = reads[User]
  implicit val writer = writes[User]
}

@Entity
@Table(name = "tax_rates")
case class TaxRate(
                    @(Id@field)
                    @(GeneratedValue@field)
                    (strategy = GenerationType.IDENTITY)
                    id: L = null,
                    description: String = "",
                    @(NotNull@field)
                    amount: Long = 0L
                    ) extends Model {}

object TaxRate {

  import play.api.libs.functional.syntax._

  implicit val reader = (
    (__ \ 'id).readNullable[L] and
      (__ \ 'description).read[String] and
      (__ \ 'amount).read[Long])((i, d, a) => {
    TaxRate.apply(i.orNull, d, a)
  })
  implicit val writer = writes[TaxRate]
}

@Entity
@Table(name = "invoices")
case class Invoice(
                    @(Id@field)
                    @(GeneratedValue@field)
                    (strategy = GenerationType.IDENTITY)
                    id: L = null,
                    number: String = ""
                    ) extends Model {
  @OneToMany
  var lineItems: util.List[LineItem] = new util.ArrayList[LineItem]()
}


object Invoice {
  implicit val reader = reads[Invoice]
  implicit val writer = writes[Invoice]
}

@Entity
@Table(name = "products")
case class Product(
                    @(Id@field)
                    @(GeneratedValue@field)
                    (strategy = GenerationType.IDENTITY)
                    id: L = null,
                    @(NotNull@field)
                    description: String = "",
                    @(Column@field)(name = "unit_price")
                    unitPrice: Long = 0L,
                    @(ManyToOne@field)(cascade = Array(CascadeType.PERSIST, CascadeType.MERGE))
                    @(JoinColumn@field)(name = "tax_id", referencedColumnName = "id")
                    @(NotNull@field)
                    tax: TaxRate = new TaxRate()
                    ) extends Model {}

object Product {
  implicit val writer = writes[Product]
  implicit val reader = reads[Product]
}

@Entity
@Table(name = "line_items")
case class LineItem(
                     @(Id@field)
                     @(GeneratedValue@field)
                     (strategy = GenerationType.IDENTITY)
                     id: L = null,
                     @(ManyToOne@field)
                     @(JoinColumn@field)(name = "product_id", referencedColumnName = "id")
                     @(NotNull@field)
                     product: Product = new Product(),
                     quantity: Int = 0,
                     amount: Long = 0L
                     ) extends Model {
  @ManyToOne var invoice: Invoice = _
}

object LineItem {
  implicit val reader = reads[LineItem]
  implicit val writer = writes[LineItem]
}

