package lectures.collections

/**
  * Постарайтесь не использовать мутабильные коллекции и var
  * Подробнее о сортировке можно подсмотреть здесь - https://en.wikipedia.org/wiki/Merge_sort
  *
  */
object MergeSortImpl extends App {

  def merge(first: Seq[Int], second: Seq[Int]): Seq[Int] = (first, second) match {
    case (x, Nil) => x
    case (Nil, y) => y
    case (xh :: xt, yh :: yt) => if (xh < yh) xh +: merge(xt, second) else yh +: merge(first, yt)
  }

  def mergeSort(data: Seq[Int]): Seq[Int] = data.length match {
    case 0 | 1 => data
    case n => val (left, right) = data.splitAt(n / 2); merge(mergeSort(left), mergeSort(right))
  }

  println(mergeSort(Seq(4,2,3,5)).mkString(","))

}
