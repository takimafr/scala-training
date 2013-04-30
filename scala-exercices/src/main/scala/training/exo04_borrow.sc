package training

import java.io.{ Closeable, File, FileInputStream }

object exo04_borrow {

  type Foo = Function1[String, Double]

  def withCloseable(closeable: Closeable)(block: Closeable => Unit) = try {
    block(closeable)
  } finally {
    closeable.close
  }                                               //> withCloseable: (closeable: java.io.Closeable)(block: java.io.Closeable => Un
                                                  //| it)Unit

  withCloseable(new FileInputStream(new File("."))) { file =>

  }                                               //> java.io.FileNotFoundException: . (Is a directory)
                                                  //| 	at java.io.FileInputStream.open(Native Method)
                                                  //| 	at java.io.FileInputStream.<init>(FileInputStream.java:138)
                                                  //| 	at training.exo04_borrow$$anonfun$main$1.apply$mcV$sp(training.exo04_bor
                                                  //| row.scala:15)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at training.exo04_borrow$.main(training.exo04_borrow.scala:7)
                                                  //| 	at training.exo04_borrow.main(training.exo04_borrow.scala)
}