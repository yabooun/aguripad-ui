package controller

import _root_.controller.admin._
import _root_.controller.api.v1.CropPictureController
import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    adminmenu.mount(ctx)
    processes.mount(ctx)
    comments.mount(ctx)
    consumers.mount(ctx)
    fans.mount(ctx)
    crops.mount(ctx)
    farmers.mount(ctx)
    root.mount(ctx)
    cropApi.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }

  object adminmenu extends AdminController with Routes {
    val indexUrl = get("/admin")(index).as('admin)
  }

  object farmers extends FarmersController with Routes {
  }

  object crops extends CropsController with Routes {
  }

  object fans extends FansController with Routes {
  }

  object consumers extends ConsumersController with Routes {
  }

  object comments extends CommentsController with Routes {
  }

  object processes extends ProcessesController with Routes {
  }

  object cropApi extends CropPictureController with Routes {
    val getPicture = get("/api/v1/crop_picture")(getLastPicture).as('crop_picture)
    val postPicture = post("/api/v1/upload_picture/:crop_id")(uploadPicture).as('upload_picture)
  }

}
