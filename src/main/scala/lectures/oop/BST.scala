package lectures.oop


/**
  * BSTImpl - это бинарное дерево поиска, содержащее только значения типа Int
  *
  * * Оно обладает следующими свойствами:
  * * * * * левое поддерево содержит значения, меньшие значения родителя
  * * * * * правое поддерево содержит значения, большие значения родителя
  * * * * * значения, уже присутствующие в дереве, в него не добавляются
  * * * * * пустые значения (null) не допускаются
  *
  * * Завершите реализацию методов кейс класс BSTImpl:
  * * * * * Трейт BST и BSTImpl разрешается расширять любым образом
  * * * * * Изменять сигнатуры классов и методов, данные в условии, нельзя
  * * * * * Постарайтесь не использовать var и мутабильные коллекции
  * * * * * В задаче про распечатку дерева, нужно раскомментировать и реализовать метод toString()
  *
  * * Для этой структуры нужно реализовать генератор узлов.
  * * Генератор:
  * * * * * должен создавать дерево, содержащее nodesCount узлов.
  * * * * * не должен использовать переменные или мутабильные структуры.
  *
  */
trait BST {
  val value: Int
  val left: Option[BST]
  val right: Option[BST]

  def add(newValue: Int): BST

  def find(value: Int): Option[BST]

  def fold(acc: Int)(f: (Int, Int) => Int): Int

  def max: Int

  def height: Int
}

case class BSTImpl(value: Int,
                   left: Option[BSTImpl] = None,
                   right: Option[BSTImpl] = None) extends BST {

  def addImpl(newValue: Int): BSTImpl = (value, left, right) match {
    case (node, _, _) if node == newValue => this
    case (node, None, _) if newValue < node => BSTImpl(node, Option(BSTImpl(newValue, None, None)), right)
    case (node, _, None) if newValue > node => BSTImpl(node, left, Option(BSTImpl(newValue, None, None)))
    case (node, Some(l), _) if newValue < node => BSTImpl(value, Option(l.addImpl(newValue)), right)
    case (node, _, Some(r)) if newValue > node => BSTImpl(value, left, Option(r.addImpl(newValue)))
  }

  override def add(newValue: Int): BST = addImpl(newValue)

  override def find(value: Int): Option[BST] = value match {
    case node if node == value => Some(this)
    case node if node > value => left.flatMap(_.find(value))
    case node if node < value => right.flatMap(_.find(value))
    case _ => None
  }

  override def fold(acc: Int)(f: (Int, Int) => Int): Int = f(acc, foldImpl(f))

  def foldImpl(f: (Int, Int) => Int): Int = (value, left, right) match {
    case (cur, Some(l), Some(r)) => f(l.foldImpl(f), f(cur, r.foldImpl(f)))
    case (cur, Some(l), None) => f(cur, l.foldImpl(f))
    case (cur, None, Some(r)) => f(cur, r.foldImpl(f))
    case (cur, None, None) => cur
  }

  override def height: Int = 1 + Math.max(left.fold(0)(_.height), right.fold(0)(_.height))

  override def max: Int = Math.max(value, right.fold(value)(_.max))

  override def toString(): String = {
    val space = " " * max.toString().length()
    val treeRepr: Array[Array[String]] = Array.fill(height)(Array.fill((1 << height) - 1)(space))

    def fillTreeRepr(tree: Array[Array[String]], root: BSTImpl, level: Int, l: Int, r: Int): Unit = {
      tree(level)((l + r) / 2) = root.value.toString
      root.left.foreach(leftTree => fillTreeRepr(tree, leftTree, level + 1, l, (l + r) / 2))
      root.right.foreach(rightTree => fillTreeRepr(tree, rightTree, level + 1, (l + r + 1) / 2, r))
    }

    fillTreeRepr(treeRepr, this, 0, 0, (1 << height) - 1)

    treeRepr.map(line => line.mkString).mkString("\n")
  }

}

object TreeTest extends App {

  val sc = new java.util.Scanner(System.in)
  val maxValue = 110000
  val nodesCount = sc.nextInt()

  val markerItem = (Math.random() * maxValue).toInt
  val markerItem2 = (Math.random() * maxValue).toInt
  val markerItem3 = (Math.random() * maxValue).toInt

  // Generate huge tree
  val root: BST = BSTImpl(maxValue / 2)
  val tree: BST = (1 to nodesCount).foldLeft(root)((node, _) => node.add((Math.random() * maxValue).toInt)) // generator goes here

  // add marker items
  val testTree = tree.add(markerItem).add(markerItem2).add(markerItem3)
  // check that search is correct
  require(testTree.find(markerItem).isDefined)
  require(testTree.find(markerItem2).isDefined)
  require(testTree.find(markerItem3).isDefined)

  println(testTree)
}