package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class ProcessesController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.processes, "/*")

  override def afterAll() {
    super.afterAll()
    Process.deleteAll()
  }

  def newProcess = FactoryGirl(Process).create()

  it should "show processes" in {
    get("/processes") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/processes/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/processes.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/processes.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a process in detail" in {
    get(s"/processes/${newProcess.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/processes/${newProcess.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/processes/${newProcess.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/processes/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a process" in {
    post(s"/processes",
      "crop_id" -> Int.MaxValue.toString(),
      "post_date" -> skinny.util.DateTimeUtil.toString(new DateTime()),
      "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
      "image_url" -> "dummy",
      "comment" -> "dummy",
      "particular" -> Int.MaxValue.toString(),
      "sensor_json" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/processes",
        "crop_id" -> Int.MaxValue.toString(),
        "post_date" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "image_url" -> "dummy",
        "comment" -> "dummy",
        "particular" -> Int.MaxValue.toString(),
        "sensor_json" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Process.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/processes/${newProcess.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a process" in {
    put(s"/processes/${newProcess.id}",
      "crop_id" -> Int.MaxValue.toString(),
      "post_date" -> skinny.util.DateTimeUtil.toString(new DateTime()),
      "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
      "image_url" -> "dummy",
      "comment" -> "dummy",
      "particular" -> Int.MaxValue.toString(),
      "sensor_json" -> "dummy") {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/processes/${newProcess.id}",
        "crop_id" -> Int.MaxValue.toString(),
        "post_date" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "image_url" -> "dummy",
        "comment" -> "dummy",
        "particular" -> Int.MaxValue.toString(),
        "sensor_json" -> "dummy",
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a process" in {
    delete(s"/processes/${newProcess.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/processes/${newProcess.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
