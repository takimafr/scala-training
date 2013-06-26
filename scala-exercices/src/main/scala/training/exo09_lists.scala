package training

object exo09_lists {

  val list = List(1, 2, 34, 56, 90)               //> list  : List[Int] = List(1, 2, 34, 56, 90)

  def lastBuiltIn(list: List[Int]) = list.last    //> lastBuiltIn: (list: List[Int])Int
  lastBuiltIn(list)                               //> res0: Int = 90
  def lastRecurs(list: List[Int]): Int = list match {
    case Nil => throw new UnsupportedOperationException
    case h :: Nil => h
    case h :: tail => lastRecurs(tail)
  }                                               //> lastRecurs: (list: List[Int])Int
  lastRecurs(list)                                //> res1: Int = 90

  def penlastBuiltIn(list: List[Int]) = list(list.size - 2)
                                                  //> penlastBuiltIn: (list: List[Int])Int
  penlastBuiltIn(list)                            //> res2: Int = 56

  def nthBuiltIn(list: List[Int], n: Int) = list(n)
                                                  //> nthBuiltIn: (list: List[Int], n: Int)Int
  nthBuiltIn(list, 3)                             //> res3: Int = 56

  def nthRecurs(list: List[Int], n: Int): Int = (list, n) match {
    case (Nil, _) => throw new UnsupportedOperationException
    case (h :: tail, 0) => h
    case (_ :: tail, n) => nthRecurs(tail, n - 1)
  }                                               //> nthRecurs: (list: List[Int], n: Int)Int

  nthRecurs(list, 3)                              //> res4: Int = 56

  def lengthRecurs(list: List[Int]): Int = list match {
    case Nil => 0
    case h :: t => 1 + lengthRecurs(t)
  }                                               //> lengthRecurs: (list: List[Int])Int
  
  def lengthFunc(list: List[Int]) = list.foldLeft(0)((l, _) => l + 1)
                                                  //> lengthFunc: (list: List[Int])Int
  lengthFunc(list)                                //> res5: Int = 5
}