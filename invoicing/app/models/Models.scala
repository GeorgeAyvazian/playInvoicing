package models

import java.util
import javax.persistence._
import javax.validation.constraints.NotNull

import play.api.libs.json.Json._
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
  implicit val writer = new Writes[TaxRate] {
    override def writes(o: TaxRate): JsValue = JsObject(Seq(
      ("id", if (o.id == null) JsNull else JsNumber(o.id.longValue())),
      ("description", JsString(Option(o.description).getOrElse(""))),
      ("amount", JsNumber(o.amount))
    ))
  }
}

@Entity
@Table(name = "invoices")
case class Invoice(
                    @(Id@field)
                    @(GeneratedValue@field)
                    (strategy = GenerationType.IDENTITY)
                    id: L = null,
                    number: String = "",
                    total: L = 0L,
                    @(OneToMany@field)(cascade = Array(CascadeType.PERSIST, CascadeType.MERGE))
                    @(NotNull@field) lineItems: java.util.List[LineItem] = new util.ArrayList[LineItem]()
                    ) extends Model {}

object Invoice {
  import play.api.libs.functional.syntax._

  implicit val reader = (
    (__ \ 'id).readNullable[L] and
      (__ \ 'number).read[String] and
      (__ \ 'total).read[Long] and
      (__ \ 'lineItems).read[java.util.List[LineItem]]) ((i, d, a, l) => {
    Invoice.apply(i.orNull, d, a, l)
  })
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
  implicit lazy val writer = new Writes[Product] {
    override def writes(o: Product): JsValue = JsObject(Seq(
      ("id", if (o.id == null) JsNull else JsNumber(o.id.longValue())),
      ("description", JsString(Option(o.description).getOrElse(""))),
      ("unitPrice", JsNumber(o.unitPrice)),
      ("taxRate", Json toJson Option(o.tax).getOrElse(new TaxRate()))
    ))
  }
  implicit lazy val reader = reads[Product]
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
                     ) extends Model {}

object LineItem {
  implicit val reader = reads[LineItem]
  implicit val writer = writes[LineItem]
  import scala.collection.JavaConverters._
  implicit def lineItemListWriter:Writes[java.util.List[LineItem]] = new Writes[java.util.List[LineItem]] {
    def writes(as: java.util.List[LineItem]) = JsArray(as.asScala.map(toJson(_)).toSeq)
  }

  implicit def lineItemListReader:Reads[java.util.List[LineItem]] = new Reads[java.util.List[LineItem]] {
    def reads(json: JsValue)  = json match {
      case JsArray(ts) =>
        import scala.collection.JavaConverters._
        JsSuccess (ts.map(t => fromJson[LineItem](t)).map(j => j.get).asJava)
    }
  }
}

