package training

object exo07_cars {

  abstract class Car {
    def drive { print("Driving ") }
  }

  trait OilCar extends Car {
    override def drive {
      super.drive
      println("an oil car")
    }
  }

  trait ElectricCar extends Car {
    override def drive {
      super.drive
      println("an electric car")
    }
  }

  trait LoggingCar extends Car {
    override def drive {
      println("entering")
      super.drive
      println("exiting")
    }
  }

  (new Car with OilCar with LoggingCar).drive     //> entering
                                                  //| Driving an oil car
                                                  //| exiting
  (new Car with ElectricCar with LoggingCar).drive//> entering
                                                  //| Driving an electric car
                                                  //| exiting
  (new Car with LoggingCar with OilCar).drive     //> entering
                                                  //| Driving exiting
                                                  //| an oil car
}