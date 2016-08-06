package repositories.repo

import javax.inject.Singleton

import org.mongodb.morphia.Datastore
import repositories.mongo.MongoStore

/**
  * Created by amans on 4/8/16.
  */



class UnitOfWork {

  private val ds: Datastore = MongoStore.instance

  val userRepository = new UserRepository(ds)
  val commentRepo = new CommentRepository(ds)
  val skillRepository = new SkillRepository(ds)
  val roleRepository = new RoleRepository(ds)
  val bidRepository = new BidRepository(ds)
  val jobRepository = new JobRepository(ds)

}
