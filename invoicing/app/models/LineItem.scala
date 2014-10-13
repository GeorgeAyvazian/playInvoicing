package models

import javax.persistence._
import javax.validation.constraints.NotNull

import play.api.libs.json._
import play.db.ebean.Model

import scala.annotation.meta.field

@Entity
@Table(name = "line_items")
case class LineItem(
                     @(Id@field)
                     @(GeneratedValue@field) id: Long = null.asInstanceOf[Long],
                     @(ManyToOne@field) @(JoinColumn@field)(name = "product_id", referencedColumnName = "id") @(NotNull@field) product: Product = new Product(),
                     quantity: Int = 0,
                     amount: Long = 0L
                     )
  extends Model {
  @ManyToOne var invoice: Invoice = _
}

object LineItem {
  implicit val reader = Json.reads[LineItem]
  implicit val writer = Json.writes[LineItem]
}