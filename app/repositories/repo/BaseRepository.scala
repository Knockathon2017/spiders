package repositories.repo

import org.bson.types.ObjectId
import org.mongodb.morphia.{Key, Datastore}
import org.mongodb.morphia.dao.BasicDAO
import utils.{Constants, InvalidObjectId}
import scala.collection.JavaConversions._
import scala.concurrent.Future
import scala.reflect._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by amans on 4/8/16.
  */
class BaseRepository[T: ClassTag](ds: Datastore)
  extends BasicDAO[T, ObjectId](classTag[T].runtimeClass.asInstanceOf[Class[T]], ds) {


  def findById(id: String): Option[T] = {
    val oId = this.parseObjectId(id)
    val entity = this.get(oId)

    Option(entity)
  }

  def findByIdAsync(id: String): Future[Option[T]] = {
    Future {
      this.findById(id)
    }
  }

  protected def parseObjectId(value: String): ObjectId = {

    if (!ObjectId.isValid(value))
      throw new InvalidObjectId(value)

    new ObjectId(value)
  }

  def getAllAsync(offset: Int = 0, limit: Int = Constants.DefaultPageSize): Future[Seq[T]] = {
    Future {
      this.find().asList.toSeq.slice(offset, limit)

    }
  }


//  def saveAsync(entity: T): Future[T] = {
//    Future {
//      this.save(entity)
//      entity
//
//    }
//  }
//
//  override def save(entity: T): Key[T] = {
//    super.save(entity)
//  }

}
