package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    comments.mount(ctx)
    consumers.mount(ctx)
    fans.mount(ctx)
    processes.mount(ctx)
    crops.mount(ctx)
    farmers.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }

  object farmers extends _root_.controller.FarmersController with Routes {
  }

  object crops extends _root_.controller.CropsController with Routes {
  }

  object processes extends _root_.controller.ProcessesController with Routes {
  }

  object fans extends _root_.controller.FansController with Routes {
  }

  object consumers extends _root_.controller.ConsumersController with Routes {
  }

  object comments extends _root_.controller.CommentsController with Routes {
  }

}
