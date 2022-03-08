package u02

object Es7 extends App:
  
  enum Shape:
    case Rectangle(weight: Double, height: Double)
    case Circle(radius: Double)
    case Square(l: Double)

  object Shape:
    def perimeter(shape: Shape): Double = shape match
      case Rectangle(w,h) => (w+h)*2
      case Circle(r) => 2*3.14*r
      case Square(l) => l*4

    def area(shape: Shape): Double = shape match
      case Rectangle(w,h) => w*h
      case Circle(r) => 3.14*r*r
      case Square(l) => l*l


  println(Shape.perimeter(Shape.Rectangle(3, 3))) //12.0
  println(Shape.area(Shape.Circle(3))) //28.26
