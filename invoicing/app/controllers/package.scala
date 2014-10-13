import java.lang.{Long => L}

import models.TaxRate
import play.api.data.validation.ValidationError
import play.api.libs.json._

package object controllers {

  implicit object LReads extends Reads[L] {
    def reads(json: JsValue) = json match {
      case JsNull => JsSuccess(null.asInstanceOf[L])
      case JsNumber(n) => JsSuccess(n.asInstanceOf[L])
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("why!"))))
    }
  }

  implicit object LWrites extends Writes[L] {
    def writes(o: L) = JsNumber(o.asInstanceOf[Long])
  }

  def i[R] = scala.collection.JavaConversions.collectionAsScalaIterable[R] _

  implicit object toJs {
    implicit def toJs(v:TaxRate) = Json toJson v
  }

}
