package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime
import scala.util.{Failure, Success}
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, Uri}
import akka.http.scaladsl.Http
import akka.http.scaladsl.unmarshalling.Unmarshal
import net.yoshinorin.selfouettie.config.ConfigProvider
import net.yoshinorin.selfouettie.utils.{File, Logger}

object GitHubEventService extends ActorService with EventsConverter with EventService with ConfigProvider with Logger {

  private val api = configuration.getString("github.api")
  private val token = configuration.getString("github.token")
  private val store = configuration.getBoolean("github.storeResult")

  def getEvents(): Unit = {

    val request = HttpRequest(
      HttpMethods.GET,
      uri = Uri(api),
      headers = List(Authorization(OAuth2BearerToken(token)))
    )

    Http().singleRequest(request).onComplete {
      case Success(s) => {
        Unmarshal(s.entity).to[String].onComplete {
          case Success(json) => {
            if (this.store) {
              this.storeJson(json)
            }
            convert(json) match {
              case Some(x) => {
                x.foreach(y => create(y))
              }
              case None => logger.info("GitHub Events is nothing.")
            }
          }
          case Failure(throwable: Throwable) => logger.error(throwable.toString)
        }
      }
      case Failure(throwable: Throwable) => logger.error(throwable.toString)
    }
  }

  private[this] def storeJson(json: String): Unit = {
    val filePath = System.getProperty("user.dir") + "/src/main/resources/data/store/" + ZonedDateTime.now.toEpochSecond.toString + ".json"
    File.create(filePath) match {
      case Success(_) => File.write(filePath, json)
      case Failure(_) => logger.error("GitHub event data failed to store file.")
    }
  }

}