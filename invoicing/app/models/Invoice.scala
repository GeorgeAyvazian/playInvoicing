package models

import java.util
import javax.persistence._

import play.api.libs.json._
import play.db.ebean.Model

import scala.annotation.meta.field

@Entity
@Table(name = "invoices")
case class Invoice(
                    @(Id@field) @(GeneratedValue@field) id: Long = null.asInstanceOf[Long],
                    number: String = ""
                    )

  extends Model {
  @OneToMany
  var lineItems: util.List[LineItem] = new util.ArrayList[LineItem]()
}

object Invoice {
  implicit val reader = Json.reads[Invoice]
  implicit val writes = Json.writes[Invoice]

}