package training

object exo08_cars {

  trait Cake
  class Cookie extends Cake
  class Pie extends Cake

  trait CakeFactory[+T] {
    def bake: T
  }

  val pieFactory = new CakeFactory[Pie] {
    def bake = new Pie
  }                                               //> pieFactory  : training.exo08_cars.CakeFactory[training.exo08_cars.Pie] = tra
                                                  //| ining.exo08_cars$$anonfun$main$1$$anon$1@f7000ed

  val cookieFactory = new CakeFactory[Cookie] {
    def bake = new Cookie
  }                                               //> cookieFactory  : training.exo08_cars.CakeFactory[training.exo08_cars.Cookie]
                                                  //|  = training.exo08_cars$$anonfun$main$1$$anon$2@2838305b

  class Glutton[-T] {
    def eat(cake: T) { println(s"eating a ${cake.getClass.getSimpleName.toLowerCase}") }
  }

  val pieFactoryAsCakeFactory: CakeFactory[Cake] = pieFactory
                                                  //> pieFactoryAsCakeFactory  : training.exo08_cars.CakeFactory[training.exo08_ca
                                                  //| rs.Cake] = training.exo08_cars$$anonfun$main$1$$anon$1@f7000ed
  val cookieGlutton = new Glutton[Cookie]         //> cookieGlutton  : training.exo08_cars.Glutton[training.exo08_cars.Cookie] = t
                                                  //| raining.exo08_cars$$anonfun$main$1$Glutton$1@6403b70c

  val cakeGlutton = new Glutton[Cake]             //> cakeGlutton  : training.exo08_cars.Glutton[training.exo08_cars.Cake] = train
                                                  //| ing.exo08_cars$$anonfun$main$1$Glutton$1@2fe6a820
  val cakeGluttonAsCookieGlutton: Glutton[Cookie] = cakeGlutton
                                                  //> cakeGluttonAsCookieGlutton  : training.exo08_cars.Glutton[training.exo08_car
                                                  //| s.Cookie] = training.exo08_cars$$anonfun$main$1$Glutton$1@2fe6a820
}