package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class CropsController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.crops, "/*")

  override def afterAll() {
    super.afterAll()
    Crop.deleteAll()
  }

  def newCrop = FactoryGirl(Crop).create()

  it should "show crops" in {
    get("/crops") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/crops/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/crops.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/crops.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a crop in detail" in {
    get(s"/crops/${newCrop.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/crops/${newCrop.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/crops/${newCrop.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/crops/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a crop" in {
    post(s"/crops",
      "crop_name" -> "dummy",
      "title" -> "dummy",
      "sales_text" -> "dummy",
      "good_points" -> Long.MaxValue.toString(),
      "product_image1" -> "dummy",
      "product_image2" -> "dummy",
      "product_image3" -> "dummy",
      "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/crops",
        "crop_name" -> "dummy",
        "title" -> "dummy",
        "sales_text" -> "dummy",
        "good_points" -> Long.MaxValue.toString(),
        "product_image1" -> "dummy",
        "product_image2" -> "dummy",
        "product_image3" -> "dummy",
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Crop.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/crops/${newCrop.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a crop" in {
    put(s"/crops/${newCrop.id}",
      "crop_name" -> "dummy",
      "title" -> "dummy",
      "sales_text" -> "dummy",
      "good_points" -> Long.MaxValue.toString(),
      "product_image1" -> "dummy",
      "product_image2" -> "dummy",
      "product_image3" -> "dummy",
      "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/crops/${newCrop.id}",
        "crop_name" -> "dummy",
        "title" -> "dummy",
        "sales_text" -> "dummy",
        "good_points" -> Long.MaxValue.toString(),
        "product_image1" -> "dummy",
        "product_image2" -> "dummy",
        "product_image3" -> "dummy",
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a crop" in {
    delete(s"/crops/${newCrop.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/crops/${newCrop.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
