package utils

import java.io.StringWriter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import models.TEntity
import play.api.libs.json.JsValue


object JsonProvider {

  //create mapper and register scala module
  private val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new JodaModule)

  //writes dates in ISO 8601 format
  mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

  def toJson(obj: Object): String = {
    obj match {
      case newObj: TEntity =>

        val writer = new StringWriter

        mapper.writeValue(writer, newObj)
        writer.toString

      case _ =>
    }
    val writer = new StringWriter
    mapper.writeValue(writer, obj)
    writer.toString
  }

  /** *
    * Reference - http://stackoverflow.com/questions/6200253/scala-classof-for-type-parameter
    *
    * @param json
    * @tparam T
    * @return
    */
  def fromJson[T: scala.reflect.Manifest](json: String): T = {
    mapper.readValue(json, scala.reflect.classTag[T].runtimeClass).asInstanceOf[T]
  }

  def readFromJsonMap[T](jsonData: Map[String, JsValue], fieldName: String)(implicit fjs: play.api.libs.json.Reads[T]): Option[T] = {
    jsonData.get(fieldName) match {
      case Some(x) => x.asOpt[T]
      case _ => None
    }
  }
}

