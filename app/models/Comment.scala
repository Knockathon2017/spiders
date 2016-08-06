package models

import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.{Reference, Entity}

/**
  * Created by amans on 4/8/16.
  */
@Entity("comments")
case class Comment(
                    var rating: Double,
                    @Reference var userId: String = null,
                    @Reference var creator: String = null,
                    var commentedBy: String = null,
                    var comment: String
                  ) extends TIdentity {

  protected def this() = this(0.0, null, null, null, null)
}