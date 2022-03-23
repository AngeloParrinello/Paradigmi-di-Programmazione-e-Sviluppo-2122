package u04lab.code

trait Complex:
  def re: Double
  def im: Double
  def +(c: Complex): Complex // should implement the sum of two complex numbers..
  def *(c: Complex): Complex // should implement the product of two complex numbers

object Complex:
  def apply(re: Double, im: Double): Complex = ComplexImpl(re, im) // Fill here

  private case class ComplexImpl(re: Double, im: Double) extends Complex :
    override def +(c: Complex): Complex = Complex(re + c.re, im + c.im)
    override def *(c: Complex): Complex = Complex((re * c.re) - (im * c.im), (re * c.im) + (im * c.re))

@main def checkComplex(): Unit =
  val a = Array(Complex(10, 20), Complex(1, 1), Complex(7, 0))
  val c = a(0) + a(1) + a(2)
  println((c, c.re, c.im)) // (ComplexImpl(18.0,21.0),18.0,21.0)
  val c2 = a(0) * a(1)
  println((c2, c2.re, c2.im)) // (ComplexImpl(-10.0,30.0),-10.0,30.0)

  val test = Complex(10.4, 21.4)
  val test2 = Complex(10.6, 23.5)
  val test3 = Complex(10.4, 21.4)
  println(test.equals(test2))
  println(test.equals(test3))

/** Hints:
 *   - implement Complex with a ComplexImpl class, similar to PersonImpl in slides
 *   - check that equality and toString do not work
 *   - use a case class ComplexImpl instead, creating objects without the 'new' keyword
 *   - check equality and toString now
 */
