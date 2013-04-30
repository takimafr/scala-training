package dal

import java.net.URI
import models._
import models.TeaColors._

import play.api.db._
import anorm._
import anorm.SqlParser._

trait ImplicitConnection {

  import java.sql._

  def withConnection[A](block: Connection => A): A
}

trait Users { this: ImplicitConnection =>

  val userParser = str("user.login") ~ str("user.name") map { case login ~ name => User(login, name) }

  def findUserById(login: String): Option[User] = withConnection { implicit conn =>
    SQL("select login, name from user where login = {login}")
      .on("login" -> login)
      .as(userParser.singleOpt)
  }

  def login(login: String, password: String): Boolean = withConnection { implicit conn =>
    SQL("select login from user where login = {login} and password = {password}")
      .on("login" -> login, "password" -> password)
      .as(str("user.login").singleOpt)
  }.isDefined
}

object Colors {

  val colorOptions = TeaColors.values.map(color => color.toString -> color.toString).toSeq
}

object Suppliers {
  
	val supplierParser =
			int("supplier.id") ~
			str("supplier.name") ~
			str("supplier.country") ~
			str("supplier.uri") map {
			case id ~ name ~ country ~ uri => Supplier(id, name, country, new URI(uri))
	}
}

trait Suppliers { this: ImplicitConnection =>


  val supplierOptions: Seq[(String, String)] = withConnection { implicit conn =>
    SQL("select * from supplier")
      .as(Suppliers.supplierParser *)
      .map(supplier => (supplier.id.toString, supplier.name))
  }
}

trait Teas { this: ImplicitConnection =>

  import Suppliers._

  val teaParser = int("tea.id") ~
    str("tea.name") ~
    str("tea.color") ~
    str("tea.size") ~
    str("tea.currency") ~
    get[Double]("tea.price") ~
    int("tea.supplier_id") map {
      case id ~ name ~ color ~ size ~ currency ~ price ~ supplierId => Tea(id, name, TeaColors.withName(color), size, currency.charAt(0), price, supplierId)
    }

  def allTeasWithSupplier: Iterable[(Tea, Supplier)] = withConnection { implicit conn =>
    SQL("select * from tea, supplier where tea.supplier_id = supplier.id")
      .as(teaParser ~ supplierParser *)
      .map { case tea ~ supplier => (tea, supplier) }
  }

  def findTeaById(id: Int): Option[Tea] = withConnection { implicit conn =>
    SQL("select * from tea where id = {id}")
      .on("id" -> id)
      .as(teaParser.singleOpt)
  }

  def findTeaByIdWithSupplier(id: Int): Option[(Tea, Supplier)] = withConnection { implicit conn =>
    SQL("select * from tea, supplier where tea.supplier_id = supplier.id and tea.id = {id}")
      .on("id" -> id)
      .as(teaParser ~ supplierParser singleOpt)
      .map { case tea ~ supplier => (tea, supplier) }
  }

  def deleteTea(id: Int) {
    withConnection { implicit conn =>
      SQL("delete from tea where id = {id}").on("id" -> id).executeUpdate
    }
  }

  def save(tea: Tea) {
    withConnection { implicit conn =>
      SQL("""
          insert into tea(id,name,color,size,currency,price,supplier_id)
          values (
          (select next value for tea_seq),
          {name},{color},{size},{currency},{price},{supplierId})
          """)
        .on("name" -> tea.name,
          "color" -> tea.color.toString,
          "size" -> tea.size,
          "currency" -> tea.currency,
          "price" -> tea.price,
          "supplierId" -> tea.supplierId).executeInsert()
    }
  }

  def update(theId: Int, tea: Tea) {
    withConnection { implicit conn =>
      SQL("""
          update tea
          set name = {name}, color = {color}, size = {size}, currency = {currency}, price = {price}, supplier_id = {supplierId}
          where id = {id}
          """)
        .on("id" -> theId,
          "name" -> tea.name,
          "color" -> tea.color.toString,
          "size" -> tea.size,
          "currency" -> tea.currency,
          "price" -> tea.price,
          "supplierId" -> tea.supplierId).executeUpdate
    }
  }
}


  
