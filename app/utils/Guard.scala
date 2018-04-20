package utils

class Guard(condition: => Boolean, exception: => Throwable) {
  def isViolate: Boolean = condition
  def getException: Throwable = exception
}

object Guard {
  def apply(condition: => Boolean, exception: => Throwable): Guard =
    new Guard(condition, exception)
  def failed(exception: => Exception): Guard =
    new Guard(true, exception)
  def passed: Guard = new Guard(false, new Exception("Guard passed."))
}
