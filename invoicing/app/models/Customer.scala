package models

import javax.persistence.{Entity, GeneratedValue, Id, Table}

import play.db.ebean.Model

@Entity
@Table(name = "customers")
case class Customer()
  extends Model {
  @Id
  @GeneratedValue var id: Long = _
}