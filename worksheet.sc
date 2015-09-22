import monifu.reactive.Observable
import scala.concurrent.Await
import scala.concurrent.duration._
import monifu.concurrent.Implicits.globalScheduler

Observable.interval(100.millis).buffer(70.millis).take(100).foreach(println)
