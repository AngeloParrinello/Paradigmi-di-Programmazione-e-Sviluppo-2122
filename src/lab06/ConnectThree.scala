package u06lab.code

import java.util.OptionalInt

object ConnectThree extends App :
  val bound = 3

  enum Player:
    case X, O

    def other: Player = this match
      case X => O
      case _ => X

  case class Disk(x: Int, y: Int, player: Player)

  /**
   * Board:
   * y
   *
   * 3
   * 2
   * 1
   * 0
   * 0 1 2 3 <-- x
   */
  type Board = Seq[Disk]
  type Game = Seq[Board]

  import Player.*

  def find(board: Board, x: Int, y: Int): Option[Player] = board.collectFirst { case d if d.x == x && d.y == y => d.player }

  def firstAvailableRow(board: Board, x: Int): Option[Int] =
    Some(board.collect { case d if d.x == x => d.y + 1 }.maxOption.getOrElse(0)).filter(_ <= bound)

  def placeAnyDisk(board: Board, player: Player): Seq[Board] =
    for
      x <- bound to 0 by -1
      row = this.firstAvailableRow(board, x) if row.isDefined
    yield
      board :+ Disk(x, row.get, player)

  def computeAnyGame(player: Player, moves: Int): LazyList[Game] =
    // first version: it works but show only completed board
    /*player match
      case X if moves == 1 => LazyList(placeAnyDisk(Seq(), X))
      case O if moves == 1 => LazyList(placeAnyDisk(Seq(), O))

      case O if moves > 1 =>
        for
          games <- this.computeAnyGame(X, moves - 1)
          boards <- games
        yield
          placeAnyDisk(boards, O)
      case X if moves > 1 =>
        for
          games <- this.computeAnyGame(O, moves - 1)
          boards <- games
        yield
          placeAnyDisk(boards, X)
      case _ => throw new IllegalStateException()*/

    // second version: show all the phases
    player match
      case X if moves == 1 => LazyList(placeAnyDisk(Seq(), X))
      case O if moves == 1 => LazyList(placeAnyDisk(Seq(), O))

      case X if moves == 2 =>
        for
          games <- this.computeAnyGame(O, moves - 1)
          boards <- games
          newBoards <- placeAnyDisk(boards, X)
        yield
          Seq(boards, newBoards)

      case O if moves == 2 =>
        for
          games <- this.computeAnyGame(X, moves - 1)
          boards <- games
          newBoards <- placeAnyDisk(boards, O)
        yield
          Seq(boards, newBoards)

      case X if moves > 2 =>
        for
          games <- this.computeAnyGame(O, moves - 1)
          lastBoards = games.last
          newBoards <- placeAnyDisk(lastBoards, X)
        yield
          games :+ newBoards

      case O if moves > 2 =>
        for
          games <- this.computeAnyGame(X, moves - 1)
          lastBoards = games.last
          newBoards <- placeAnyDisk(lastBoards, O)
        yield
          games :+ newBoards

      case _ => throw new IllegalStateException()

  def computeAnyGameWithWin(player: Player, moves: Int): LazyList[Game] =

    player match
      case X if moves == 1 => LazyList(placeAnyDisk(Seq(), X))
      case O if moves == 1 => LazyList(placeAnyDisk(Seq(), O))

      case X if moves == 2 =>
        for
          games <- this.computeAnyGame(O, moves - 1)
          boards <- games
          newBoards <- placeAnyDisk(boards, X)
        yield
          Seq(boards, newBoards)

      case O if moves == 2 =>
        for
          games <- this.computeAnyGame(X, moves - 1)
          boards <- games
          newBoards <- placeAnyDisk(boards, O)
        yield
          Seq(boards, newBoards)

      case X if moves > 2 =>
        for
          games <- this.computeAnyGame(O, moves - 1)
          lastBoards = games.last
          newBoards <- placeAnyDisk(lastBoards, X)
          if !hasWin(newBoards, player)
        yield
          games :+ newBoards

      case O if moves > 2 =>
        for
          games <- this.computeAnyGame(X, moves - 1)
          lastBoards = games.last
          newBoards <- placeAnyDisk(lastBoards, O)
          if !hasWin(newBoards, player)
        yield
          games :+ newBoards

      case _ => throw new IllegalStateException()

  def hasWin(board: Board, player: Player): Boolean = ???
    /*var lastDisk = board.last
    for
      disks <- board
      disk2 = board(board.indexOf(disks) + 1)
      disk3 = board(board.indexOf(disks) + 2)
      if threeConnected(disks, disk2, disk3)
    yield



    println("Win by: " + player)
    false*/

  def printBoards(game: Seq[Board]): Unit =
    for
      y <- bound to 0 by -1
      board <- game.reverse
      x <- 0 to bound
    do
      print(find(board, x, y).map(_.toString).getOrElse("."))
      if x == bound then
        print(" ")
        if board == game.head then println()

  // Exercise 1: implement find such that..
  println("EX 1: ")
  println(find(List(Disk(0, 0, X)), 0, 0)) // Some(X)
  println(find(List(Disk(0, 0, X), Disk(0, 1, O), Disk(0, 2, X)), 0, 1)) // Some(O)
  println(find(List(Disk(0, 0, X), Disk(0, 1, O), Disk(0, 2, X)), 1, 1)) // None

  // Exercise 2: implement firstAvailableRow such that..
  println("EX 2: ")
  println(firstAvailableRow(List(), 0)) // Some(0)
  println(firstAvailableRow(List(Disk(0, 0, X)), 0)) // Some(1)
  println(firstAvailableRow(List(Disk(0, 0, X), Disk(0, 1, X)), 0)) // Some(2)
  println(firstAvailableRow(List(Disk(0, 0, X), Disk(0, 1, X), Disk(0, 2, X)), 0)) // Some(3)
  println(firstAvailableRow(List(Disk(0, 0, X), Disk(0, 1, X), Disk(0, 2, X), Disk(0, 3, X)), 0)) // None
  // Exercise 2: implement placeAnyDisk such that..
  printBoards(placeAnyDisk(List(), X))
  // .... .... .... ....
  // .... .... .... ....
  // .... .... .... ....
  // ...X ..X. .X.. X...
  printBoards(placeAnyDisk(List(Disk(0, 0, O)), X))
  // .... .... .... ....
  // .... .... .... ....
  // ...X .... .... ....
  // ...O ..XO .X.O X..O
  println("EX 3: ")
  // Exercise 3 (ADVANCED!): implement computeAnyGame such that.

  computeAnyGame(O, 4).foreach { g =>
    printBoards(g)
    println()
  }
//  .... .... .... .... ...O
//  .... .... .... ...X ...X
//  .... .... ...O ...O ...O
//  .... ...X ...X ...X ...X
//
//
// .... .... .... .... O...
// .... .... .... X... X...
// .... .... O... O... O...
// .... X... X... X... X...

// Exercise 4 (VERY ADVANCED!) -- modify the above one so as to stop each game when someone won!!
 /* computeAnyGameWithWin(O, 4).foreach { g =>
    printBoards(g)
    println()
  }*/