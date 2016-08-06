package models

import org.mongodb.morphia.annotations.Entity

/**
  * Created by abhishekj on 6/8/16.
  */
@Entity("bids")
case class Bid(
                var bidId: Int,
                var jobId: Int,
                var bidCost: Double,
                var bidderMobile: String,
                var bidderName: String
              ) extends TIdentity {

  protected def this() = this(0, 0, 0.0, null, null)
}