package lectures.operators

/**
  * Проходит чемпионат по спортивному киданию костей)
  * Сражаются "Наши" и "Приезжие"
  *
  * Каждый член команды бросил кубик и должен сравнить свой результат с каждым результатом из команды соперника
  *
  * Итог сравнений должн быть записан в ассоциативный массив в таком виде
  * val results: Array[(String, Int)] = (("Artem vs John" -> 3), ("Artem vs James" -> 5), ... )
  * При этом числовое значение должно быть получено как разность между результатами первого и второго игроков
  *
  * Когда составлен массив results, надо подсчитать, чья взяла.
  * Если результат встречи >0, то finalResult увеличивается на единицу
  * Если <0, уменьшается
  *
  * В итоге надо
  * исправить ошибки компиляции
  * напечатать:
  * * "Наша взяла", если наших побед больше, т.е. finalResult > 0
  * * "Продули", если победили приезжие
  * * "Победила дружба" в случае ничьи
  *
  * Для решения задачи раскомментируйте тело объекта Competition
  * В целях упрощения можно поменять тип исходных данных
  */

object Competition extends App {

  val locals = Map("Artem" -> 6, "Sergey" -> 5, "Anton" -> 2, "Vladimir" -> "2", "Alexander" -> 4D)
  val foreigners = Map[String, Int]("John" -> 3, "James" -> 1, "Tom" -> 2, "Dick" -> 5, "Eric" -> 6)

  def convertToInt[T](a: T): Int =
    a match {
      case a: Int => a
      case a: String => a.toInt
      case a: Double => a.toInt
    }

    val results = for (l <- locals;
                       l2 <- foreigners) yield {
      val localName = l._1
      val localValue = convertToInt(l._2)
      val foreignName = l2._1
      val foreignValue = convertToInt(l2._2)
      (localName + " vs " + foreignName, localValue - foreignValue)
    }

    var finalResult = 0
    for (r <- results) {
      if (r._2 > 0) finalResult = finalResult + 1
      else if (r._2 < 0) finalResult = finalResult - 1
    }
  if (finalResult == 0) println("Победила дружба")
  if (finalResult > 0) println("Наша взяла")
  if (finalResult < 0) println("Продули")
}
