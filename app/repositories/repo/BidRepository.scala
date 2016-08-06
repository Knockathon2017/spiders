package repositories.repo

import models.Bid
import org.mongodb.morphia.Datastore
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by abhishekj on 5/8/16.
  */
class BidRepository(ds: Datastore) extends BaseRepository[Bid](ds) {

  def saveBid(bid: Bid) = {
    super.save(bid)
  }

  def get(bidId: Int) = {
    val query = this.createQuery()
    query.field("bidId").equal(bidId)
    Future {
      Option(query.get())
    }
  }

  def getBid(jobId: Int) = {
    val query = this.createQuery()
    query.field("jobId").equal(jobId)
    Future {
      Option(query.get())
    }
  }
}
