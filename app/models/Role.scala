package models

import org.mongodb.morphia.annotations.Entity

/**
  * Created by abhishekj on 5/8/16.
  */
@Entity("roles")
case class Role(   var roleId: Int,
                   var role: String
                 ) extends TIdentity {
  protected def this() = this(0,null)
}
