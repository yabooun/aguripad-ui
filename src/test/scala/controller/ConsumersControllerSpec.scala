package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class ConsumersControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Consumer.deleteAll()
  }

  def createMockController = new ConsumersController with MockController
  def newConsumer = FactoryGirl(Consumer).create()

  describe("ConsumersController") {

    describe("shows consumers") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/consumers/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/consumers/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a consumer") {
      it("shows HTML response") {
        val consumer = newConsumer
        val controller = createMockController
        controller.showResource(consumer.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Consumer]("item") should equal(Some(consumer))
        controller.renderCall.map(_.path) should equal(Some("/consumers/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/consumers/new"))
      }
    }

    describe("creates a consumer") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy")
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
      val consumer = newConsumer
      val controller = createMockController
      controller.editResource(consumer.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/consumers/edit"))
    }

    it("updates a consumer") {
      val consumer = newConsumer
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy")
      controller.updateResource(consumer.id)
      controller.status should equal(200)
    }

    it("destroys a consumer") {
      val consumer = newConsumer
      val controller = createMockController
      controller.destroyResource(consumer.id)
      controller.status should equal(200)
    }

  }

}
