package lectures.oop

import lectures.functions.SQLAPI

/**
  * У вас есть приложение, которое можно запустить в тестовом или продуктовом окружении.
  * В зависимости от окружения ваше приложение создает БД с тестовым или бовым адресом
  * и использует подходящую реализацию сервиса.
  *
  * Ваша задача: реализовать методы doSomeService в тестовом и боевом сервисах. Для этого:
  * * * * добавьте SQLAPI как self type annotation
  * * * * уберите знаки вопроса и раскомментируйте execute(sql)
  * * * * допишите тело функций в соответсвии с комментарием в каждом методе
  * После этого допишите инициализацию usefulService в Application так, чтобы:
  * в тестовой среде использовался TestServiceImpl
  * в боевой - ProductionServiceImpl
  *
  * Допишите тесты в ApplicationTest
  *
  */
trait UsefulService {
  this: SQLAPI =>

  def doSomeService(): Int
}

class TestServiceImpl(resource: String) extends SQLAPI(resource) with UsefulService {
  private val sql = "do the SQL query and then count words"

  def doSomeService(): Int = execute(sql).split(" ").length //подсчитайте количество слов в результате execute
}

class ProductionServiceImpl(resourse: String) extends SQLAPI(resourse) with UsefulService {
  private val sql = "do the SQL query and than count 'a' symbols"

  def doSomeService(): Int = execute(sql).count(_ == 'a') // подсчитайте сколько символов 'a' в полученной строке
}

class Application(isTestEnv: Boolean) {

  val usefulService: UsefulService = if (isTestEnv)
    new TestServiceImpl("test db Resource")
  else
    new ProductionServiceImpl("production Resource") //передайте "production Resource" в качестве ресурсв в конструктор SQLAPI

  def doTheJob(): Int = usefulService.doSomeService()

}
