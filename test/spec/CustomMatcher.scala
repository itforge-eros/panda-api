package spec

import org.scalatest.matchers._
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.Future
import scala.util.Try

trait CustomMatcher {

  class ClassIsAssignableToMatcher(expectedClass: Class[_]) extends Matcher[Class[_]] {

    def apply(actualClass: Class[_]): MatchResult = {
      val actualClassName = actualClass.getName
      val expectedClassName = expectedClass.getName

      MatchResult(
        expectedClass.isAssignableFrom(actualClass),
        s"""$actualClassName is not assignable to $expectedClassName""",
        s"""$actualClassName is assignable to $expectedClassName"""
      )
    }
  }

  class ResultHaveStatusMatcher(right: Int) extends Matcher[Future[Result]] {

    override def apply(left: Future[Result]): MatchResult = {
      val expectedStatus = right
      val actualStatus = status(left)

      val body = Try{
        "json response :\n" + contentAsJson(left).toString
      }.getOrElse("response cannot be parsed into JSON, manually call this api for further analysis")

      MatchResult(
        actualStatus == expectedStatus,
        s"""expected $expectedStatus, got $actualStatus
           |$body""".stripMargin,
        s"""got $expectedStatus as expected""""
      )
    }

  }

  class ResultIsJsonParsableMatcher extends Matcher[Future[Result]] {

    override def apply(left: Future[Result]): MatchResult = {
      val response = left

      val triedJsValue = Try {
        contentAsJson(response)
      }

      MatchResult(
        triedJsValue.isSuccess,
        s"""response cannot be parsed into JSON, manually call this api for further analysis""",
        s"""response can be parsed into JSON"""
      )

    }
  }

  def beAssignableTo(expectedClass: Class[_]) = new ClassIsAssignableToMatcher(expectedClass)

  def haveStatus(right: Int): Matcher[Future[Result]] = new ResultHaveStatusMatcher(right)

  def beJsonParsable: Matcher[Future[Result]] = new ResultIsJsonParsableMatcher

}

object CustomMatcher extends CustomMatcher
