package utils

import org.slf4j.LoggerFactory

trait Logging {

  private val logger = LoggerFactory.getLogger(getClass)

  def debug(message: => String) { if (logger.isDebugEnabled) logger.debug(message) }
}