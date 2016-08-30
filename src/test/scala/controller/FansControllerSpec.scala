package controller

import _root_.controller.admin.FansController
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class FansControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Fan.deleteAll()
  }

  def createMockController = new FansController with MockController
  def newFan = FactoryGirl(Fan).create()

  describe("FansController") {

    describe("shows fans") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/fans/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/fans/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a fan") {
      it("shows HTML response") {
        val fan = newFan
        val controller = createMockController
        controller.showResource(fan.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Fan]("item") should equal(Some(fan))
        controller.renderCall.map(_.path) should equal(Some("/fans/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/fans/new"))
      }
    }

    describe("creates a fan") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "consumer_id" -> Int.MaxValue.toString(),
          "farmer_id" -> Int.MaxValue.toString())
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
      val fan = newFan
      val controller = createMockController
      controller.editResource(fan.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/fans/edit"))
    }

    it("updates a fan") {
      val fan = newFan
      val controller = createMockController
      controller.prepareParams(
        "consumer_id" -> Int.MaxValue.toString(),
        "farmer_id" -> Int.MaxValue.toString())
      controller.updateResource(fan.id)
      controller.status should equal(200)
    }

    it("destroys a fan") {
      val fan = newFan
      val controller = createMockController
      controller.destroyResource(fan.id)
      controller.status should equal(200)
    }

  }

}
