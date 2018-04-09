package definitions.exceptions

trait GraphqlException {

  class GraphqlSyntaxError
    extends Exception("GraphQL syntax error.")

  object GraphqlVariablesParseError
    extends Exception("Cannot parse GraphQL variables.")

  object TooComplexQueryError
    extends Exception("Query is too expensive.")

}
