package u05lab.ex1

import org.junit.Test
import org.junit.Assert.*
import org.junit.Assert

class ListTest:

  import u05lab.ex1.List
  import List.*


  def reference: List[Int] = List(1, 2, 3, 4)

  @Test
  def testIndexOf(): Unit =
    assertEquals(0, reference.indexOf(1).get)
    assertEquals(1, reference.indexOf(2).get)
    assertEquals(2, reference.indexOf(3).get)
    assertEquals(3, reference.indexOf(4).get)

  @Test
  def testMapWithFold(): Unit =
    assertEquals(List(2, 4, 6, 8), reference.mapWithFold( _ * 2))

  @Test
  def testFilterWithFold(): Unit =
    assertEquals(List(1, 2), reference.filterWithFold(_ < 3))

  @Test
  def testZipRightRecursive(): Unit =
    assertEquals(List((1, 0), (2, 1), (3, 2), (4, 3)), reference.zipRightRecursive)

  @Test
  def testPartitionRecursive(): Unit =
    assertEquals((List(2, 4), List(1, 3)), reference.partitionRecursive(_ % 2 == 0))

  @Test
  def testSpanRecursive(): Unit =
    assertEquals((List(1), List(2, 3, 4)), reference.spanRecursive(_ % 2 != 0))

  @Test
  def testReduceRecursive(): Unit =
    assertEquals(10, reference.reduceRecursive(_ + _))
    assertEquals(10, List(10).reduceRecursive(_ + _))
    var tmp:List[Int] = Nil()
    assertThrows(classOf[UnsupportedOperationException],() => tmp.reduceRecursive(_ + _))

  @Test
  def testTakeRightRecursive(): Unit =
    assertEquals(List(3,4), reference.takeRightRecursive(2))

  @Test
  def testZipRight(): Unit = assertEquals(List((1, 0), (2, 1), (3, 2), (4, 3)), reference.zipRight)

  @Test
  def testPartition(): Unit =
    assertEquals((List(2, 4), List(1, 3)), reference.partition(_ % 2 == 0))

  @Test
  def testCollect(): Unit =
    val function: PartialFunction[Int, Int] = { case x if x >= 2 => x}
    assertEquals(List(2, 3, 4), reference.collect(function))

  @Test
  def testTakeRight(): Unit = assertEquals(List(4, 3), reference.takeRight(2))

  @Test
  def testSpan(): Unit =
    assertEquals((List(1), List(4, 3, 2)), reference.span(_ % 2 != 0))

  @Test
  def testAllMatch(): Unit =
    assertTrue(reference.allMatch(_ < 5))

  @Test
  def testAllPositive(): Unit =
    assertTrue(allPositive(reference))