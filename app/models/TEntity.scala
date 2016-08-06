package models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.mongodb.morphia.annotations.{Id, Reference}

trait TUserTrackable {

  @Reference
  var createdBy: User = null

  @Reference
  var updatedBy: User = null

  @Reference
  var deletedBy: User = null
}

trait TIdentity {
  @Id
  @JsonSerialize(using = classOf[ToStringSerializer])
  val id: ObjectId = new ObjectId

  override def equals(obj: Any) = {
    obj match {
      case objWithId: TIdentity =>
        objWithId.equals(this.id)

      case _ => false
    }
  }

}

trait TEntity extends TTimeTrackable with TIdentity {

}

trait TTimeTrackable {
  var createdAt: DateTime = DateTime.now
  var updatedAt: DateTime = DateTime.now
  var deletedAt: Option[DateTime] = None
}

trait TApiClean[T] {
  def cleanForApi: T
}