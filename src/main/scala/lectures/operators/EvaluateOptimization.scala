package lectures.operators

import lectures.functions.{Computation, CurriedComputation, Data, FunctionalComputation}

/**
  * В задачке из lectures.functions.Computations мы реализовали
  * один и тот же метод 3-мя разными способами
  *
  * Пришло время оценить, насколько разные имплементации
  * отличаются друг от друга по производительности
  *
  * Для этого
  * * в классах CurriedComputation и FunctionalComputation уберите extends App, оставьте extends Data
  * * раскомментируйте код, выполните в циклах вызов 3-х имплементаций,
  * * оцените разницу во времени выполнения и объясните ее происхожение
  *
  */
object EvaluateOptimization extends App with Data {

  val computationStartTimestamp = System.currentTimeMillis()

  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 Computation.computation
  for (_ <- 1 to 100) {
    Computation.computation(filterData, dataArray)
  }

  println("Elapsed time in computation(): " + (System.currentTimeMillis() - computationStartTimestamp))

//  Elapsed time in computation(): 11778


  val partiallyAppliedStartTimestamp = System.currentTimeMillis()

  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 CurriedComputation.partiallyAppliedCurriedFunction
  for (_ <- 1 to 100) {
    CurriedComputation.partiallyAppliedCurriedFunction(dataArray)
  }

  val partiallyAppliedDuration = System.currentTimeMillis() - partiallyAppliedStartTimestamp
  println("Elapsed time in partiallyAppliedCurriedFunction(): " + partiallyAppliedDuration)

//  Elapsed time in partiallyAppliedCurriedFunction(): 11539

  val filterAppliedStartTimestamp = System.currentTimeMillis()

  // ВЫПОЛНИТЬ В ЦИКЛЕ ОТ 1 ДО 100 FunctionalComputation.filterApplied
  for (_ <- 1 to 100) {
    FunctionalComputation.filterApplied(dataArray)
  }

  val filterAppliedDuration = System.currentTimeMillis() - filterAppliedStartTimestamp
  println("Elapsed time in filterApplied():" + filterAppliedDuration)

//  Elapsed time in filterApplied():124

  // ВЫВЕСТИ РАЗНИЦУ В ПРОДОЛЖИТЕЛЬНОСТИ ВЫПОЛНЕНИЯ МЕЖДУ КАРРИРОВАННОЙ ВЕРСИЕЙ
  // И ФУНКЦИОНАЛЬНОЙ

  val diff = partiallyAppliedDuration - filterAppliedDuration

  println(s"Difference is about $diff milliseconds")

//  Difference is about 11415 milliseconds
//  Когда мы вызываем каррированную версию, каждый раз заново вычисляется curriedComputation и выполняются долгие слипы,
//  при вызовах функциональной версии, слипы выполнились один раз при первом вызове,
//  а дальше использовался только результат этой функции
}

