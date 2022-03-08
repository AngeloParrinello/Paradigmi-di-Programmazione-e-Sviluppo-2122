package u02

object Es3 extends App:
  //------------3c-------------------

  def negGen[X](fun: X => Boolean): (X => Boolean) = s => !fun(s)

  val emptyGen: Int => Boolean = _ == 1 // predicate on strings
  val notEmptyGen = negGen(emptyGen) // which type of notEmpty?
  println("print 3c")
  println(notEmptyGen(1)) // false
  println(notEmptyGen(2)) // true
  println(notEmptyGen(1) && !notEmptyGen(2)) // false
