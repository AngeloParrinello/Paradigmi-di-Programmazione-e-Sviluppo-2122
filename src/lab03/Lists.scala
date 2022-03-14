package u03

import u02.Modules.Person
import u02.Optionals.Option
import u02.Optionals.Option.*

object Lists extends App:

  // A generic linkedlist
  enum List[E]:
    case Cons(head: E, tail: List[E])
    case Nil()
  // a companion object (i.e., module) for List
  object List:

    def sum(l: List[Int]): Int = l match
      case Cons(h, t) => h + sum(t)
      case _ => 0

    def map[A, B](l: List[A])(mapper: A => B): List[B] = l match
      case Cons(h, t) => Cons(mapper(h), map(t)(mapper))
      case Nil() => Nil()

    def filter[A](l1: List[A])(pred: A => Boolean): List[A] = l1 match
      case Cons(h, t) if pred(h) => Cons(h, filter(t)(pred))
      case Cons(_, t) => filter(t)(pred)
      case Nil() => Nil()

    /*
    *
    * Lab works
    *
    */

    def drop[A](l: List[A], n: Int): List[A] = l match
      case Cons(h, t) if n > 0 => drop(t, n-1)
      case Cons(h, t) => Cons(h,t)
      case Nil() => Nil()

    def append[A](left: List[A], right: List[A]): List[A] = left match
      case Cons(h, t) => Cons(h, append(t, right))
      case _ => right

    def flatMap[A,B](l: List[A])(f: A => List[B]): List[B] = l match
      case Cons(h, t) => append(f(h), flatMap(t)(f))
      case _ => Nil()

    def mapWithFlatMap[A, B](l: List[A])(mapper: A => B): List[B] =
      flatMap(l)(v => Cons(mapper(v), Nil()))

    def filterWithFlatMap[A](l1: List[A])(pred: A => Boolean): List[A] =
      flatMap(l1)( v => v match
        case v if pred(v) => Cons(v, Nil())
        case _ => Nil()
      )

    def max(l: List[Int]): Option[Int] = l match
      case Cons(h, Cons(h2, t2)) if (h >= h2) => max(Cons(h, t2))
      case Cons(h, Cons(h2, t2)) if (h < h2) => max(Cons(h2, t2))
      case Cons(h, Nil()) =>  Some(h)
      case _ => None()

    def teachersCourses(person: List[Person]): List[String] =
      flatMap(person)(v => v match
        case Person.Teacher(name, course) => Cons(course, Nil())
        case _ => Nil()
      )

    def foldLeft[A, B](list: List[A])(acc: B)(op: (B, A) => B): B = list match
      case Cons(h, t) => foldLeft(t)(op(acc, h))(op)
      case _ => acc

    def foldRight[A, B](list: List[A])(acc: B)(op: (A, B) => B): B = list match
      case Cons(h, Nil()) => op(h, acc)
      case Cons(h, t) => op(h, foldRight(t)(acc)(op))
      case _ => acc


