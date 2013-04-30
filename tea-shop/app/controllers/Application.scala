package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Security._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format._
import play.api.data.format.Formats._
import play.api.data.validation.Constraints._
import models._
import play.api.db._
import dal.Colors

object DefaultDAL {
  import dal._
  import java.sql._
  import play.api.Play.current

  trait DefaultConnection extends ImplicitConnection {
    def withConnection[A](block: Connection => A): A = DB.withConnection(block)
  }

  val Users = new Users with DefaultConnection
  val Suppliers = new Suppliers with DefaultConnection
  val Teas = new Teas with DefaultConnection
}

object TeaCommand {

  def apply(tea: Tea): TeaCommand = TeaCommand(
    id = tea.id,
    name = tea.name,
    color = tea.color.toString,
    size = tea.size,
    currency = tea.currency.toString,
    price = tea.price,
    supplierId = tea.supplierId)
}

case class TeaCommand(id: Int, name: String, color: String, size: String, currency: String, price: Double, supplierId: Int) {

  def toTea = Tea(
    id = this.id,
    name = this.name,
    color = TeaColors.withName(this.color),
    size = this.size,
    currency = this.currency.charAt(0),
    price = this.price,
    supplierId = this.supplierId)
}

object Application extends Controller {

  import DefaultDAL._

  val Home = Redirect(routes.Application.index)

  val List = Redirect(routes.Application.list)

  object LoginForm {

    val login = "login"
    val password = "password"
  }

  def userinfo(request: RequestHeader) = request.session.get(LoginForm.login).flatMap(Users.findUserById)

  def onUnauthorized(request: RequestHeader) = Home.flashing(("message", "Please login first"))

  val isAuthenticated = Authenticated(
    userinfo,
    onUnauthorized) _

  val loginForm = {
    import LoginForm._
    Form(
      tuple(
        LoginForm.login -> text.verifying(nonEmpty),
        password -> text.verifying(nonEmpty))
        .verifying("Invalid login/password",
          loginPassword => Users.login(loginPassword._1, loginPassword._2)))
  }

  def index = Action { implicit request =>
    Ok(views.html.index(loginForm))
  }

  def login = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      loginAndPassword => {
        List.withSession(LoginForm.login -> loginAndPassword._1)
      })
  }

  def logout = isAuthenticated { user =>
    Action {
      Home.withNewSession
    }
  }

  def list = isAuthenticated { user =>
    Action {
      val teas = Teas.allTeasWithSupplier
      Ok(views.html.list(teas))
    }
  }

  def view(id: Int) = isAuthenticated { user =>
    Action {
      val teaWithSupplier = Teas.findTeaByIdWithSupplier(id)

      teaWithSupplier match {
        case Some((tea, supplier)) => Ok(views.html.view(tea, supplier))
        case None => NotFound(s"Unknown tea id $id")
      }
    }
  }

  object TeaForm {

    val id = "id"
    val name = "name"
    val color = "color"
    val size = "size"
    val currency = "currency"
    val price = "price"
    val supplierId = "supplierId"
  }

  val teaForm = {
    import TeaForm._
    Form(
      mapping(
        id -> ignored(0),
        name -> text.verifying(nonEmpty),
        color -> text.verifying(nonEmpty),
        size -> text.verifying(nonEmpty),
        currency -> nonEmptyText(1, 1),
        price -> of[Double],
        supplierId -> number)(TeaCommand.apply)(TeaCommand.unapply))
  }

  def create = isAuthenticated { user =>
    Action {
      Ok(views.html.create(Suppliers.supplierOptions, Colors.colorOptions, teaForm))
    }
  }

  def save = isAuthenticated { user =>
    Action { implicit request =>
      teaForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.create(Suppliers.supplierOptions, Colors.colorOptions, formWithErrors))
        },
        tea => {
          Teas.save(tea.toTea)
          List
        })
    }
  }

  def edit(id: Int) = isAuthenticated { user =>
    Action {
      Teas.findTeaById(id) match {
        case Some(tea) =>
          Ok(views.html.edit(id, Suppliers.supplierOptions, Colors.colorOptions, teaForm.fill(TeaCommand(tea))))
        case None => NotFound(s"Tea $id not found")
      }
    }
  }

  def update(id: Int) = isAuthenticated { user =>
    Action { implicit request =>
      teaForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.edit(id, Suppliers.supplierOptions, Colors.colorOptions, formWithErrors))
        },
        tea => {
          Teas.update(id, tea.toTea)
          List
        })
    }
  }

  def delete(id: Int) = isAuthenticated { user =>
    Action {
      Teas.deleteTea(id)
      List
    }
  }
}