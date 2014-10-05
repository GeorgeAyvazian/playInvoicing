package models

import javax.persistence.{Column, Entity, GeneratedValue, Id, Table}

import play.api.libs.json.JsValue
import play.db.ebean.Model

import scala.annotation.meta.field

@Entity
@Table(name = "somemodels")
case class SomeModel(@(Column@field)(name = "firstName") firstName: String = "", @(Column@field)(name = "lastName") lastName: String = "", @(Column@field)(name = "dob") dob: String = "", @(Id@field) @(GeneratedValue@field) id: Long = 0L)
  extends Model {

  def this() = this("", "", "")
}

object SomeModel {
  def create(jsSomeModel: JsValue): Long = {
    val firstName = (jsSomeModel \ "firstName").as[String]
    val lastName = (jsSomeModel \ "lastName").as[String]
    val dob = (jsSomeModel \ "dob").as[String]
    val newSomeModel: SomeModel = SomeModel apply(firstName, lastName, dob)
    newSomeModel save()
    newSomeModel.id
  }


  import play.api.libs.json.{JsArray, Json}

  implicit val writer = Json.writes[SomeModel]

  import play.db.ebean.Model.Finder

  private val finder: (Finder[Long, SomeModel]) = new Finder(classOf[Long], classOf[SomeModel])

  def findAll: JsArray = {
    import scala.collection.JavaConversions.collectionAsScalaIterable
    Json arr collectionAsScalaIterable(finder all).toList
  }

  def delete(id: Long) = {
    finder setId id
    (finder byId id).delete()
  }
}