package u02

object Es4 extends App:
  def p4(x: Int, y: Int, z: Int): Boolean = x <= y && y <= z
  
  println(p4(7,8,9)) // true

  def p3(x: Int)(y: Int)(z: Int): Boolean = x <= y && y <= z

  println(p3(7)(8)(9)) // true

  val p2 = (x: Int, y: Int, z: Int) => x <= y && y <= z

  println(p2(7,8,9)) // true

  val p1 : Int => Int => Int => Boolean = x => y => z => (x <= y && y <= z)

  println(p1(7)(8)(9)) // true

  val test = p1(7)
  println(test(8)(9)) //true