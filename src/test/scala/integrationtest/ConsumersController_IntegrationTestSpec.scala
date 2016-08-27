package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class ConsumersController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.consumers, "/*")

  override def afterAll() {
    super.afterAll()
    Consumer.deleteAll()
  }

  def newConsumer = FactoryGirl(Consumer).create()

  it should "show consumers" in {
    get("/consumers") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/consumers/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/consumers.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/consumers.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a consumer in detail" in {
    get(s"/consumers/${newConsumer.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/consumers/${newConsumer.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/consumers/${newConsumer.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/consumers/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a consumer" in {
    post(s"/consumers",
      "name" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/consumers",
        "name" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Consumer.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/consumers/${newConsumer.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a consumer" in {
    put(s"/consumers/${newConsumer.id}",
      "name" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/consumers/${newConsumer.id}",
        "name" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a consumer" in {
    delete(s"/consumers/${newConsumer.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/consumers/${newConsumer.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
