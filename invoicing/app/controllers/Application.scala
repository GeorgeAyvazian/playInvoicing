package controllers

import play.api.mvc.{Action, Controller}

object Application extends Controller {

  def main(s: String) = Action {
    Ok(views.html.main())
  }

  def getURI(any: String): String = any match {
    case s: String => "/public/partials/" + s + ".html"
  }

  /** load an HTML page from public/html */
  def loadPublicHTML(any: String) = Action {
    import play.Play
    val projectRoot = Play.application.path
    import java.io.File
    val file = new File(projectRoot + getURI(any))
    file.exists match {
      case true => Ok(scala.io.Source.fromFile(file.getCanonicalPath).mkString) as "text/html"
      case _ => NotFound
    }
  }
}