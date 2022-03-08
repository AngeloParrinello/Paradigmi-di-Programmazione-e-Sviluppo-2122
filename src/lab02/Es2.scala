package u02

object Es2 extends App:
  //------------3b-------------------

  def neg(fun: String => Boolean): (String => Boolean) = s => !fun(s)

  val empty: String => Boolean = _ == "" // predicate on strings
  val notEmpty = neg(empty) // which type of notEmpty?
  println("print 3b")
  println(notEmpty("foo")) // true
  println(notEmpty("")) // false
  println(notEmpty("foo") && !notEmpty("")) // true.. a comprehensive test
