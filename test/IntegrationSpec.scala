import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

/**
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends PlaySpec with OneServerPerTest with OneBrowserPerTest with HtmlUnitFactory {

  "Application" should {
    "work twice" in {
      go to ("http://localhost:" + port)
      pageSource must equal("*" * 1024)

      withClue("Expected to serve two requests, but abruptly failed on the second") {
        go to ("http://localhost:" + port)
        pageSource must equal("*" * 1024)
      }
    }
  }
}
