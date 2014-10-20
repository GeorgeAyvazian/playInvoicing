import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.mvc.Results._
import play.api.mvc._

package object controllers {

  type L = java.lang.Long

  implicit object LReads extends Reads[L] {
    def reads(json: JsValue) = json match {
      case JsNull => JsSuccess(null)
      case JsNumber(n) => JsSuccess(n.asInstanceOf[L])
      case _ => JsError(Seq(JsPath() -> Seq(ValidationError("why!"))))
    }
  }

  implicit object LWrites extends Writes[L] { def writes(o: L) = JsNumber(o.asInstanceOf[Long]) }

  def i[R] = scala.collection.JavaConversions.collectionAsScalaIterable[R] _

  implicit class any(v: Any) { implicit def and[E](ret: E) = ret }

  def AuthenticatedAction(f: Request[AnyContent] => Result) = Action { r =>
    val session = r.session + ("user" → "george_a@fastmail.fm")
    session.get("user") match {
      case Some(x) => f(r).withSession("user" → "george_a@fastmail.fm")
      case _ => Redirect(routes.Application.login)
    }
  }
}
