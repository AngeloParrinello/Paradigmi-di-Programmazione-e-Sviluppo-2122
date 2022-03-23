package u04lab.polyglot

import org.junit.Test
import org.junit.*
import org.junit.Assert.*

class TestLogicsImpl:

  import u04lab.polyglot.a01b.*
  import u04lab.code.List.*
  import u04lab.code.List

  @Test
  def testDeployMines() =
    var implTest: LogicsImpl = LogicsImpl(4, 2)
    implTest.deployMines()
    assertEquals(2, length(implTest.mineSet))

  @Test
  def testHit() =
    var implTest: LogicsImpl = LogicsImpl(4, 0)
    implTest.mineSet = Cons(Tuple2(0, 0), Nil())
    assertEquals(1, implTest.hit(1, 1).get())

  @Test
  def testNeighbours() =
    var implTest: LogicsImpl = LogicsImpl(4, 0)
    implTest.mineSet = Cons(Tuple2(0, 0), Nil())
    assertEquals(0, implTest.hit(4, 4).get())

  @Test
  def testHasWon() =
    var implTest: LogicsImpl = LogicsImpl(2, 0)
    implTest.mineSet = Cons(Tuple2(0, 0), Cons(Tuple2(0,1), Cons(Tuple2(1, 0), Nil())))
    implTest.hit(1, 1)
    assertTrue(implTest.won)





