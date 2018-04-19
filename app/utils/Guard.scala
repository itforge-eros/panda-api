package utils

class Guard(condition: => Boolean, exception: => Exception) {
  def isViolate: Boolean = condition
  def getException: Exception = exception
}

object Guard {
  def apply(condition: => Boolean, exception: => Exception): Guard =
    new Guard(condition, exception)
  def failed(exception: => Exception): Guard =
    new Guard(true, exception)
  def passed: Guard = new Guard(false, new Exception("Guard passed."))
}
