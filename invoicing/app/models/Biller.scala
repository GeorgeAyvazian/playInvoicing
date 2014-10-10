package models

import javax.persistence.{Entity, GeneratedValue, Id, Table}

import play.db.ebean.Model

@Entity
@Table(name = "billers")
case class Biller()
  extends Model {
  @Id
  @GeneratedValue var id: Long = _
}