package controllers

import java.io.File

import play.Play.application
import play.api.mvc.{Action, Controller}

import scala.io.Source.fromFile

object Application extends Controller {

  def main(s: String) = Action { Ok(views.html.main()) }

  def getURI(any: String) = any match { case s => "/public/partials/" + s + ".html" }

  def loadPublicHTML(any: String) = Action {
    val projectRoot = application.path
    val file = new File(projectRoot + getURI(any))
    file.exists match { case true => Ok(fromFile(file getCanonicalPath) mkString) as "text/html" case _ => NotFound }
  }

  def getPdf = Action { Ok sendFile(new File("/home/george/books/algorithms.pdf"), true) }
}