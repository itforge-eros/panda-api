package config.components

import play.api.ApplicationLoader.Context
import play.api.{BuiltInComponentsFromContext, LoggerConfigurator}

abstract class BuiltInComponentsWithLogging(context: Context) extends BuiltInComponentsFromContext(context) {

  LoggerConfigurator(context.environment.classLoader) foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

}
