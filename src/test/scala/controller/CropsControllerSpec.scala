package controller

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class CropsControllerSpec extends FunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll() {
    super.afterAll()
    Crop.deleteAll()
  }

  def createMockController = new CropsController with MockController
  def newCrop = FactoryGirl(Crop).create()

  describe("CropsController") {

    describe("shows crops") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/crops/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/crops/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a crop") {
      it("shows HTML response") {
        val crop = newCrop
        val controller = createMockController
        controller.showResource(crop.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Crop]("item") should equal(Some(crop))
        controller.renderCall.map(_.path) should equal(Some("/crops/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/crops/new"))
      }
    }

    describe("creates a crop") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "crop_name" -> "dummy",
          "title" -> "dummy",
          "sales_text" -> "dummy",
          "good_points" -> Long.MaxValue.toString(),
          "product_image1" -> "dummy",
          "product_image2" -> "dummy",
          "product_image3" -> "dummy",
          "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()))
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
      val crop = newCrop
      val controller = createMockController
      controller.editResource(crop.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/crops/edit"))
    }

    it("updates a crop") {
      val crop = newCrop
      val controller = createMockController
      controller.prepareParams(
        "crop_name" -> "dummy",
        "title" -> "dummy",
        "sales_text" -> "dummy",
        "good_points" -> Long.MaxValue.toString(),
        "product_image1" -> "dummy",
        "product_image2" -> "dummy",
        "product_image3" -> "dummy",
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()))
      controller.updateResource(crop.id)
      controller.status should equal(200)
    }

    it("destroys a crop") {
      val crop = newCrop
      val controller = createMockController
      controller.destroyResource(crop.id)
      controller.status should equal(200)
    }

  }

}
