package models

import java.net.URI

object TeaColors extends Enumeration {
  type Color = Value
  val Black, White, Oolong, Green = Value
}

case class Supplier(id: Int, name: String, country: String, uri: URI)

case class Tea(id: Int, name: String, color: TeaColors.Color, size: String, currency: Char, price: Double, supplierId: Int)

case class User(login: String, name: String)