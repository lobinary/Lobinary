package org.slf4j;

public class LoggerFactory {
	
	public static Logger getLogger(Class<?> clazz) {
		Logger logger = new Logger();
		logger.clazz = clazz;
		return logger;
	}

}
