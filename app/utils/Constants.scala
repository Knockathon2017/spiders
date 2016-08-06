package utils

import scala.concurrent.duration.Duration

/**
  * Created by amans on 4/8/16.
  */
object Constants {

  val DefaultPageSize: Int = 50
  val DefaultDurationForSynchronousExecution: Duration = Duration("10 s")

  val LongDurationForSchronousExecutoin: Duration = Duration("60 s")

}
