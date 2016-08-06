package repositories.repo

import models.Job
import org.mongodb.morphia.Datastore
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by abhishekj on 5/8/16.
  */
class JobRepository(ds: Datastore) extends BaseRepository[Job](ds) {

  def saveJob(job: Job) = {
    super.save(job)
  }

  def get(jobId: Int) = {
    val query = this.createQuery()
    query.field("jobId").equal(jobId)
    Future {
      Option(query.get())
    }
  }

}
