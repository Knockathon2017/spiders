package repositories.mongo

import com.mongodb.{MongoClientURI, MongoClient}
import org.mongodb.morphia.{Morphia, Datastore}
import play.api.Play
import utils.PlayCreator
import play.api.Play.current

/**
  * Created by amans on 4/8/16.
  */
object MongoStore {

  private val _mongoClient = new MongoClient(new MongoClientURI(Play.configuration.getString("mongo.url").getOrElse("")))

  private var _datastore: Datastore = null

  def instance: Datastore = {

    if (_datastore == null) {

      val morphia: Morphia = new Morphia()

      // create the Datastore connecting to the default port on the local host

      // To prevent problem during hot-reload, this wont be fired in beta/prod
      if (Play.isDev)
        morphia.getMapper.getOptions.setObjectFactory(new PlayCreator)

      _datastore = morphia.createDatastore(
        _mongoClient,
        Play.configuration.getString("mongo.dbName").getOrElse("")
      )

      morphia.getMapper.getConverters.addConverter(classOf[JodaDateTimeConverter])
      morphia.getMapper.getConverters.addConverter(classOf[OptionConverter])
      morphia.getMapper.getConverters.addConverter(classOf[BigDecimalConverter])
      morphia.getMapper.getConverters.addConverter(classOf[SeqConverter])

      _datastore.ensureIndexes()
    }

    _datastore
  }
}
