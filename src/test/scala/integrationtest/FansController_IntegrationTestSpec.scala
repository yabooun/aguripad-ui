package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class FansController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.fans, "/*")

  override def afterAll() {
    super.afterAll()
    Fan.deleteAll()
  }

  def newFan = FactoryGirl(Fan).create()

  it should "show fans" in {
    get("/fans") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/fans/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/fans.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/fans.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a fan in detail" in {
    get(s"/fans/${newFan.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/fans/${newFan.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/fans/${newFan.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/fans/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a fan" in {
    post(s"/fans",
      "consumer_id" -> Int.MaxValue.toString(),
      "farmer_id" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/fans",
        "consumer_id" -> Int.MaxValue.toString(),
        "farmer_id" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Fan.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/fans/${newFan.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a fan" in {
    put(s"/fans/${newFan.id}",
      "consumer_id" -> Int.MaxValue.toString(),
      "farmer_id" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/fans/${newFan.id}",
        "consumer_id" -> Int.MaxValue.toString(),
        "farmer_id" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a fan" in {
    delete(s"/fans/${newFan.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/fans/${newFan.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
