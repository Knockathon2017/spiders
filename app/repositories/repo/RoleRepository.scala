package repositories.repo


import models.Role
import org.mongodb.morphia.Datastore

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

/**
  * Created by abhishekj on 5/8/16.
  */
class RoleRepository(ds: Datastore) extends BaseRepository[Role](ds) {

  def saveRole(role: Role) = {
        super.save(role)
  }

  def get(roleId: Int) = {
    val query = this.createQuery()
    query.field("roleId").equal(roleId)
    Future {
      Option(query.get())
    }
  }


}
