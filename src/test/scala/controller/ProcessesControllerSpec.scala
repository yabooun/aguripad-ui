package controller

import _root_.controller.admin.ProcessesController
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class ProcessesControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Process.deleteAll()
  }

  def createMockController = new ProcessesController with MockController
  def newProcess = FactoryGirl(Process).create()

  describe("ProcessesController") {

    describe("shows processes") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/processes/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/processes/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a process") {
      it("shows HTML response") {
        val process = newProcess
        val controller = createMockController
        controller.showResource(process.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Process]("item") should equal(Some(process))
        controller.renderCall.map(_.path) should equal(Some("/processes/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/processes/new"))
      }
    }

    describe("creates a process") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "crop_id" -> Int.MaxValue.toString(),
          "post_date" -> skinny.util.DateTimeUtil.toString(new DateTime()),
          "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
          "image_url" -> "dummy",
          "comment" -> "dummy",
          "particular" -> Int.MaxValue.toString(),
          "sensor_json" -> "dummy")
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be >(0)
      }
    }

    it("shows a resource edit input form") {
      val process = newProcess
      val controller = createMockController
      controller.editResource(process.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/processes/edit"))
    }

    it("updates a process") {
      val process = newProcess
      val controller = createMockController
      controller.prepareParams(
        "crop_id" -> Int.MaxValue.toString(),
        "post_date" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "image_url" -> "dummy",
        "comment" -> "dummy",
        "particular" -> Int.MaxValue.toString(),
        "sensor_json" -> "dummy")
      controller.updateResource(process.id)
      controller.status should equal(200)
    }

    it("destroys a process") {
      val process = newProcess
      val controller = createMockController
      controller.destroyResource(process.id)
      controller.status should equal(200)
    }

  }

}
