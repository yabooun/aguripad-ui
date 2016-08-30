package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class CommentsController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.comments, "/*")

  override def afterAll() {
    super.afterAll()
    Comment.deleteAll()
  }

  def newComment = FactoryGirl(Comment).create()

  it should "show comments" in {
    get("/comments") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/comments/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/comments.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/comments.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a comment in detail" in {
    get(s"/comments/${newComment.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/comments/${newComment.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/comments/${newComment.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/comments/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a comment" in {
    post(s"/comments",
      "comment_body" -> "dummy",
      "crop_id" -> Int.MaxValue.toString(),
      "process_id" -> Int.MaxValue.toString(),
      "consumer_id" -> Int.MaxValue.toString(),
      "farmer_id" -> Int.MaxValue.toString(),
      "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/comments",
        "comment_body" -> "dummy",
        "crop_id" -> Int.MaxValue.toString(),
        "process_id" -> Int.MaxValue.toString(),
        "consumer_id" -> Int.MaxValue.toString(),
        "farmer_id" -> Int.MaxValue.toString(),
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Comment.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/comments/${newComment.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a comment" in {
    put(s"/comments/${newComment.id}",
      "comment_body" -> "dummy",
      "crop_id" -> Int.MaxValue.toString(),
      "process_id" -> Int.MaxValue.toString(),
      "consumer_id" -> Int.MaxValue.toString(),
      "farmer_id" -> Int.MaxValue.toString(),
      "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/comments/${newComment.id}",
        "comment_body" -> "dummy",
        "crop_id" -> Int.MaxValue.toString(),
        "process_id" -> Int.MaxValue.toString(),
        "consumer_id" -> Int.MaxValue.toString(),
        "farmer_id" -> Int.MaxValue.toString(),
        "post_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a comment" in {
    delete(s"/comments/${newComment.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/comments/${newComment.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
