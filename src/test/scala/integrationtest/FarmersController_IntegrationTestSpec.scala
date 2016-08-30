package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class FarmersController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.farmers, "/*")

  override def afterAll() {
    super.afterAll()
    Farmer.deleteAll()
  }

  def newFarmer = FactoryGirl(Farmer).create()

  it should "show farmers" in {
    get("/farmers") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/farmers/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/farmers.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/farmers.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a farmer in detail" in {
    get(s"/farmers/${newFarmer.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/farmers/${newFarmer.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/farmers/${newFarmer.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/farmers/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a farmer" in {
    post(s"/farmers",
      "name" -> "dummy",
      "maister_farmer_id" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/farmers",
        "name" -> "dummy",
        "maister_farmer_id" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Farmer.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/farmers/${newFarmer.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a farmer" in {
    put(s"/farmers/${newFarmer.id}",
      "name" -> "dummy",
      "maister_farmer_id" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/farmers/${newFarmer.id}",
        "name" -> "dummy",
        "maister_farmer_id" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a farmer" in {
    delete(s"/farmers/${newFarmer.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/farmers/${newFarmer.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
