package u03

import org.junit.*
import org.junit.Assert.*
import u02.Optionals.Option
import u02.Optionals.Option.*
import Lists.*
import u02.Modules.Person

class ListTest:
  import List.*

  val l: List[Int] = Cons(10, Cons(20, Cons(30, Nil())))

  @Test def testSum() =
    assertEquals(0, sum(Nil()))
    assertEquals(60, sum(l))

  @Test def testMap() =
    assertEquals(Cons(11, Cons(21, Cons(31, Nil()))), map(l)(_ + 1))
    assertEquals(Cons("10", Cons("20", Cons("30", Nil()))), map(l)(_ + ""))

  @Test def testFilter() =
    assertEquals(Cons(20, Cons(30, Nil())), filter(l)(_ >= 20))
    assertEquals(Cons(10, Cons(30, Nil())), filter(l)(_ != 20))

  @Test def testDrop() =
    assertEquals(Cons (20 , Cons (30 , Nil ())), drop(l, 1))
    assertEquals(Cons (30 , Nil ()), drop(l, 2))
    assertEquals(Nil (), drop(l, 5))

  @Test def testAppend() =
    val tail = Cons (40 , Nil () )
    assertEquals(Cons (10 , Cons (20 , Cons (30 , Cons (40 , Nil ())))) , append ( l, tail ))

  @Test def testFlatMap() =
    assertEquals(Cons (11 , Cons (21 , Cons (31 , Nil ()))), flatMap ( l )(v => Cons ( v + 1, Nil () )))
    assertEquals(Cons (11 , Cons (12 , Cons (21 , Cons (22 , Cons (31 , Cons (32 , Nil ())))))),
      flatMap ( l )(v => Cons ( v + 1, Cons (v + 2, Nil () ))))

  @Test def testMapWithFlatMap() =
    assertEquals(Cons(11, Cons(21, Cons(31, Nil()))), mapWithFlatMap(l)(_ + 1))
    assertEquals(Cons("10", Cons("20", Cons("30", Nil()))), mapWithFlatMap(l)(_ + ""))

  @Test def testFilterWithFlatMap() =
    assertEquals(Cons(20, Cons(30, Nil())), filterWithFlatMap(l)(_ >= 20))
    assertEquals(Cons(10, Cons(30, Nil())), filterWithFlatMap(l)(_ != 20))

  @Test def testMax() =
    assertEquals(None(), max(Nil()))
    assertEquals(Some(25), max(Cons (10, Cons(25, Cons(20, Nil())))))

  @Test def testTeachersCourses() =
    val person = Cons( Person.Teacher("Angelo", "LCMC"), Cons( Person.Student("Culo", 2345), Nil()))
    assertEquals(Cons("LCMC", Nil()), teachersCourses(person))

  @Test def testFoldLeft() =
    val lst = Cons (3 , Cons (7 , Cons (1 , Cons (5 , Nil () ) ) ) )
    assertEquals(-16, foldLeft( lst )(0)( _ - _ ))


  @Test def testFoldRight() =
    val lst = Cons(3, Cons(7, Cons(1, Cons(5, Nil()))))
    assertEquals(-8, foldRight(lst)(0)(_-_))




