package models

import java.util

import jdk.nashorn.internal.ir.annotations.Reference
import org.mongodb.morphia.annotations.{Entity, Transient}

import scala.collection.JavaConversions._
import scala.util.Random

@Entity("users")
case class User
(
  var firstName: String = null,
  var lastName: String = null,
  var userName: String = null,
  var mobile: String = null,
  var email: String = null,
  var gender: String,
  var password: String,
  var lat: String,
  var long: String,
  var rating: Double
) extends TIdentity {
  protected def this() = this(null, null, null, null, null, null, null, null, null, 0.0)

  @Reference var role: Role = null

  @Reference var skills: java.util.List[Skill] = new util.ArrayList
}


