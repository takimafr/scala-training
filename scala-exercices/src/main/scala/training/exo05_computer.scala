package training

object exo05_computer {

  sealed trait Expression
  case class Value(value: Int) extends Expression
  case class Add(a: Expression, b: Expression) extends Expression
  case class Multiply(a: Expression, b: Expression) extends Expression

  def evaluate(expression: Expression): Int = expression match {
    case Value(value) => value
    case Add(a, b) => evaluate(a) + evaluate(b)
    case Multiply(a, b) => evaluate(a) * evaluate(b)
  }                                               //> evaluate: (expression: training.exo05_computer.Expression)Int
  
  val expression = Add(Multiply(Value(3), Value(4)), Multiply(Value(2), Value(3)))
                                                  //> expression  : training.exo05_computer.Add = Add(Multiply(Value(3),Value(4)),
                                                  //| Multiply(Value(2),Value(3)))
  
  evaluate(expression)                            //> res0: Int = 18
}