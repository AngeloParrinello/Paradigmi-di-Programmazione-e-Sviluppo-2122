package u04lab.polyglot.a01b

import scala.jdk.javaapi.OptionConverters
import u04lab.polyglot.OptionToOptional
import u04lab.code.Option
import u04lab.code.Option.*

import scala.util.Random
import u04lab.code.List.*
import u04lab.code.List

/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:
  var mineSet: List[Tuple2[Int, Int]] = Nil()
  var selected: List[Tuple2[Int, Int]] = Nil()
  val random = Random()
  deployMines()

  def deployMines(): Unit = length(mineSet) match
    case x if x < mines =>
      val newMine = Tuple2(random.nextInt(size), random.nextInt(size))
      if contains(mineSet, newMine) then deployMines() else mineSet = append(mineSet, Cons(newMine, Nil()))
      deployMines()
    case _ => println(mineSet)

  def neighbours(x: Int, y: Int): Int =
    var nearCells: List[Tuple2[Int, Int]] = Nil()
    for
      n <-  x-1 to x+1
      m <-  y-1 to y+1
    do
        nearCells = append(nearCells, Cons((n, m), Nil()))
    var n = filter(nearCells)(x => contains(mineSet, x))
    length(n)

  def hit(x: Int, y: Int): java.util.Optional[Integer] =
    if contains(mineSet, Tuple2(x, y)) then OptionToOptional(None()) else
      selected = append(selected, Cons(Tuple2(x, y), Nil()))
      OptionToOptional(Some(neighbours(x, y)))

  def won = length(selected) + length(mineSet) == size * size
