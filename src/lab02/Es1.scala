package u02

object Es1 extends App:
  //------------3a-------------------
  println("print 3a")
  val f: Int => String = _ match
    case even if even % 2 == 0 => "even"
    case _ => "odd"

  println(f(1))

  def parity(x: Int): String = x match
    case even if even % 2 == 0 => "even"
    case _ => "odd"

  println(parity(0))

  val g: (Int => String) =
    (x: Int ) => x % 2 == 0 match
      case true => "even"
      case false => "odd"

  println(g(12))
