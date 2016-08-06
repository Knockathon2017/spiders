package models

import org.mongodb.morphia.annotations.Entity

/**
  * Created by amans on 4/8/16.
  */
@Entity("skills")
case class Skill(   var skillId: Int,
                    var skillName: String
                  ) extends TIdentity {
  protected def this() = this(0,null)
}
