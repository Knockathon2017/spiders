package models

import org.mongodb.morphia.annotations.{Entity}

/**
  * Created by abhishekj on 6/8/16.
  */
@Entity("jobs")
case class Job(
                    var jobId: Int,
                    var creatorName: String = null,
                    var creatorMobile: String = null,
                    var jobTitle: String,
                    var jobDescription: String,
                    var skillId: Int
                  ) extends TIdentity {

  protected def this() = this(0, null, null, null, null, 0)
}