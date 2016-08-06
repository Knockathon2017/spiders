package services

import com.google.inject.Inject
import models.Location
import repositories.repo.UnitOfWork

/**
  * Created by amans on 5/8/16.
  */
class LocationService @Inject()(uow: UnitOfWork) {

  def updateLocation(mobile: String, lat: String, long: String) = {
    uow.userRepository.updateLocation(mobile, lat, long)
  }

}

object LocationService {

  implicit class Implicits(x: Location) {
    def getDistance(latitude: Double, longitude: Double): Double = {

      val earthRadius = 6371.0
      val lat1 = latitude.toRadians
      val lng1 = longitude.toRadians
      val lat2 = x.lat.toRadians
      val lng2 = x.long.toRadians

      val dlon: Double = lng2 - lng1
      val dlat: Double = lat2 - lat1

      val a: Double = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2)

      val c: Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

      earthRadius * c

    }


  }

}
