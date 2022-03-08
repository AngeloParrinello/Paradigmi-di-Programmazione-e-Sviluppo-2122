package u02

object Es5 extends App:
  // It's possible to define def and val with same name
  // def compose ...

  val compose:  (f: Int => Int,g: Int => Int) => Int => Int  = (f,g) => x => f(g(x))

  println(compose(_ - 1, _ * 2)(5)) // 9

  // is impossibile to define a generic function literal
  // val composeGen[X]:  (f: X => X,g: X => X) => X => X  = (f,g) => x => f(g(x))
  def composeGen[X, Y, Z](f: Y => Z,g: X => Y): (X => Z) = x => f(g(x))

  println(composeGen( x => s"$x Hello World!" ,y => 5.7)(12.0))
