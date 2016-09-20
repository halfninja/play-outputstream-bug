package controllers

import java.io.OutputStream
import javax.inject._

import akka.stream.scaladsl.{Source, StreamConverters}
import play.api._
import play.api.libs.iteratee.Enumerator
import play.api.libs.streams.Streams
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject() extends Controller {

  def index = Action {
    val source = brokenSource
    Ok.chunked(source).as("text/plain")
  }

  private def brokenSource = StreamConverters.asOutputStream().mapMaterializedValue(write)

  private def workingSource = {
    val enumerator = Enumerator.outputStream(write)
    Source.fromPublisher(Streams.enumeratorToPublisher(enumerator))
  }

  def write(out: OutputStream) {
    try {
      val bytes = ("*" * 1024).getBytes
      out.write(bytes)
    } finally {
      out.flush()
      out.close()
    }
  }

}
