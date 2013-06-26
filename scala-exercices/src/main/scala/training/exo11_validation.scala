package training

object exo11_validation {

  sealed trait Validation[+T] {

    def map[A](f: T => A): Validation[A]
    def flatMap[A](f: T => Validation[A]): Validation[A]
    def foreach[A](f: T => A) { map(f) }
  }

  case class Success[+T](value: T) extends Validation[T] {

    def map[A](f: T => A): Validation[A] = Success(f(value))
    def flatMap[A](f: T => Validation[A]): Validation[A] = f(value)
  }

  case class Failure(message: String) extends Validation[Nothing] {

    def map[A](f: Nothing => A): Validation[A] = this
    def flatMap[A](f: Nothing => Validation[A]): Validation[A] = this
  }

  case class Request
  case class Form
  case class Data
  case class Page

  class Controller {

    def parseForm(request: Request): Validation[Form] = Success(new Form)
    def accessDB(form: Form): Validation[Data] = Failure("accessDB failed!") //Success(new Data)
    def buildPage(data: Data): Validation[Page] = Success(new Page)

    def serve(request: Request) {
      /* val page: Validation[Page] = for {
        form <- parseForm(request)
        data <- accessDB(form)
        page <- buildPage(data)
      } yield page*/

      val page = parseForm(request)
        .flatMap(accessDB)
        .flatMap(buildPage)

      println(page)
    }
  }

  new Controller().serve(new Request)             //> Failure(accessDB failed!)
}