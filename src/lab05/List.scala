package u05lab.ex1

import u05lab.ex1.List

import java.util.Optional
import scala.annotation.tailrec

enum List[A]:
  case ::(h: A, t: List[A])
  case Nil()

  def ::(h: A): List[A] = List.::(h, this)

  def head: Option[A] = this match
    case h :: t => Some(h)
    case _ => None

  def tail: Option[List[A]] = this match
    case h :: t => Some(t)
    case _ => None

  def append(list: List[A]): List[A] =
    foldRight(list)(_ :: _)

  def foreach(consumer: A => Unit): Unit = this match
    case h :: t => consumer(h); t.foreach(consumer)
    case _ =>

  def get(pos: Int): Option[A] = this match
    case h :: t if pos == 0 => Some(h)
    case h :: t if pos > 0 => t.get(pos - 1)
    case _ => None

  def filter(predicate: A => Boolean): List[A] = this match
    case h :: t if predicate(h) => h :: t.filter(predicate)
    case h :: t => t.filter(predicate)
    case _ => Nil()

  def map[B](fun: A => B): List[B] = this match
    case h :: t => fun(h) :: t.map(fun)
    case _ => Nil()

  def flatMap[B](f: A => List[B]): List[B] = foldRight[List[B]](Nil())(f(_) append _)

  def foldLeft[B](z: B)(op: (B, A) => B): B = this match
    case h :: t => t.foldLeft(op(z, h))(op)
    case Nil() => z

  def foldRight[B](z: B)(f: (A, B) => B): B = this match
    case h :: t => f(h, t.foldRight(z)(f))
    case _ => z

  def length: Int = foldLeft(0)((l, _) => l + 1)

  def isEmpty: Boolean = this match
    case Nil() => true
    case _ => false

  def reverse(): List[A] = foldLeft[List[A]](Nil())((l, e) => e :: l)

  /** EXERCISES: methods with Recursion */
  def zipRightRecursive: List[(A, Int)] =
    def _zipRightRecursive(list: List[A])(acc: Int): List[(A, Int)] = list match
      case h :: t => (h, acc) :: _zipRightRecursive(t)(acc + 1)
      case _ => Nil()

    _zipRightRecursive(this)(0)

  def partitionRecursive(pred: A => Boolean): (List[A], List[A]) =
    var listTrue: List[A] = Nil()
    var listFalse: List[A] = Nil()

    @tailrec
    def _partitionRecursive(list: List[A])(pred: A => Boolean): (List[A], List[A]) = list match
      case h :: t if pred(h) => listTrue = h :: listTrue; _partitionRecursive(t)(pred)
      case h :: t => listFalse = h :: listFalse; _partitionRecursive(t)(pred)
      case _ => (listTrue, listFalse)

    _partitionRecursive(this.reverse())(pred)

  def spanRecursive(pred: A => Boolean): (List[A], List[A]) =
    var listBefore: List[A] = Nil()

    @tailrec
    def _spanRecursive(list: List[A])(pred: A => Boolean): (List[A], List[A]) = list match
      case h :: t if pred(h) => listBefore = h :: listBefore; _spanRecursive(t)(pred)
      case h :: t => (listBefore, h :: t)
      case _ => (Nil(), Nil())

    _spanRecursive(this)(pred)

  /** @throws UnsupportedOperationException if the list is empty */
  def reduceRecursive(op: (A, A) => A): A =
    @tailrec
    def _reduceRecursive[B](list: List[A])(z: B)(op: (B, A) => B): B = list match
      case h :: t => _reduceRecursive(t)(op(z, h))(op)
      case Nil() => z

    if (!this.isEmpty) then _reduceRecursive(this)(null.asInstanceOf[A])(op) else throw UnsupportedOperationException("The input list is empty!")

  def takeRightRecursive(n: Int): List[A] = n match
    case n if n > 0 => this.get(this.length - n).get :: this.takeRightRecursive(n - 1)
    case _ => Nil()

  /** EXERCISES: methods with foldLeft/Right, filter, ecc */
  /*
  Con foldLeft (o foldRight) parto da due liste vuote: ogni volta che il predicato è vero aggiungi al primo, se è falso il primo
  diventa nil e il secondo è l'elemento con il primo aggiunto il secondo
*/
  def span(pred: A => Boolean): (List[A], List[A]) =
    val result: (List[A], List[A], Boolean) = this.foldLeft(Nil(), Nil(), true)((acc, elem) =>
      if pred(elem) && acc._3
      then (elem :: acc._1, Nil(), true)
      else (acc._1, elem :: acc._2, false))
    (result._1, result._2)

  def zipRight: List[(A, Int)] = this.map(x => (x, indexOf(x).get))

  def partition(pred: A => Boolean): (List[A], List[A]) = (this.filter(pred), this.filter(!pred(_)))

  /** @throws UnsupportedOperationException if the list is empty */
  def reduce(op: (A, A) => A): A = if (!this.isEmpty) then foldLeft(null.asInstanceOf[A])(op) else throw UnsupportedOperationException("The input list is empty!")

  def takeRight(n: Int): List[A] =
    val function: PartialFunction[(A, Int), A] = {
      case x if x._2 >= this.length - n => x._1
    }
    this.reverse().map(x => (x, indexOf(x).get)).collect(function)

  def collect[B](function: PartialFunction[A, B]): List[B] = this.filter(function.isDefinedAt).map(function)

  /** EXERCISES: extra or utility methods */

  def indexOf(element: A): Option[Int] =
    @tailrec
    def _indexOf(list: List[A])(element: A)(acc: Int): Option[Int] = list match
      case h :: t if h == element => Some(acc)
      case h :: t if h != element => _indexOf(t)(element)(acc + 1)
      case _ => None

    _indexOf(this)(element)(0)

  def mapWithFold[B](fun: A => B): List[B] = foldRight(Nil())((elem, acc) => fun(elem) :: acc)

  def filterWithFold(predicate: A => Boolean): List[A] = foldRight(Nil())((elem, acc) => if predicate(elem) then elem :: acc else Nil())

  def allMatch(predicate: A => Boolean): Boolean = this.filter(predicate).length == this.length

object List:

  def allPositive(l: List[Int]): Boolean =
    l.allMatch(_ > 0)

  def apply[A](elems: A*): List[A] =
    var list: List[A] = Nil()
    for e <- elems.reverse do list = e :: list
    list

  def of[A](elem: A, n: Int): List[A] =
    if n == 0 then Nil() else elem :: of(elem, n - 1)
