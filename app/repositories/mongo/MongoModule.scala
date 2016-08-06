package repositories.mongo

import org.mongodb.morphia.Datastore
import play.api.inject.Module
import play.api.{Environment, Configuration}

/**
  * Created by amans on 4/8/16.
  */
class MongoModule extends Module {

  val ds: Datastore = MongoStore.instance

  def bindings(environment: Environment, configuration: Configuration) = {
    Seq(bind(classOf[Datastore]).toInstance(ds).eagerly())
  }
}
