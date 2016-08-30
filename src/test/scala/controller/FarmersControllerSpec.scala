package controller

import _root_.controller.admin.FarmersController
import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class FarmersControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Farmer.deleteAll()
  }

  def createMockController = new FarmersController with MockController
  def newFarmer = FactoryGirl(Farmer).create()

  describe("FarmersController") {

    describe("shows farmers") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/farmers/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/farmers/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a farmer") {
      it("shows HTML response") {
        val farmer = newFarmer
        val controller = createMockController
        controller.showResource(farmer.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Farmer]("item") should equal(Some(farmer))
        controller.renderCall.map(_.path) should equal(Some("/farmers/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/farmers/new"))
      }
    }

    describe("creates a farmer") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "maister_farmer_id" -> Int.MaxValue.toString())
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
      val farmer = newFarmer
      val controller = createMockController
      controller.editResource(farmer.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/farmers/edit"))
    }

    it("updates a farmer") {
      val farmer = newFarmer
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "maister_farmer_id" -> Int.MaxValue.toString())
      controller.updateResource(farmer.id)
      controller.status should equal(200)
    }

    it("destroys a farmer") {
      val farmer = newFarmer
      val controller = createMockController
      controller.destroyResource(farmer.id)
      controller.status should equal(200)
    }

  }

}
