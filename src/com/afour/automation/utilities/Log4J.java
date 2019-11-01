package com.afour.automation.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4J implements Reporting {
	public Logger logger;

	public Log4J() {

		logger = Logger.getLogger(Log4J.class);
		if (logFile.exists()) {
			logFile.delete();
		}

		PropertyConfigurator.configure("log4j.properties");
	}

	@Override
	public void log(String stepDescription, LogLevel logLevel) {

		switch (logLevel) {
		case INFO:
			logger.info(stepDescription);
			break;
		case ERROR:
			logger.error(stepDescription);
			break;
		case DEBUG:
			logger.debug(stepDescription);
			break;
		case TRACE:
			logger.trace(stepDescription);
			break;
		case WARN:
			logger.warn(stepDescription);
			break;
		case FATAL:
			logger.fatal(stepDescription);
			break;
		default:
			logger.info(stepDescription);
			// logger.info(message, t);
			break;
		}
	}
}
