/**
 * 
 */
package boyang.logsystem;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author boyang
 * The same LogSystem written in Java
 * 
 * The following levels taken from Javadoc:
 * OFF
 * SEVERE
 * WARNING
 * INFO
 * CONFIG
 * FINE
 * FINER
 * FINEST
 * ALL
 * 
 * To initialize, call:
 *  new LogSystem(loggerName, logFileName);
 *  or
 *  new LogSytem();
 *  
 *  To use, first get the logger by calling getLogger()
 *  then use the Java Logger obj to log
 *  See main function for example
 *  
 *  @see
 *  Also util functions:
 *  setlevel() to set all handler to a certain level
 */
public class LogSystem {
	
	private static Logger log; 
	private static String defaultLogFileName = "log.txt";
	private static String defaultLoggerName = "LogSystemLogger"; 
	
	private LogSystem() throws IOException {
		init(defaultLoggerName, defaultLogFileName);
	}
	
	private LogSystem(String loggerName, String logFileName) throws IOException {
		init(loggerName, logFileName);
	}
	
	private void init(String loggerName, String logFileName) throws IOException {
		Handler fileLoggingHandler = new FileHandler(logFileName);
		Handler consoleLoggingHandler = new ConsoleHandler();
		
		Formatter formatter = new SimpleFormatter();
		fileLoggingHandler.setFormatter(formatter);
		consoleLoggingHandler.setFormatter(formatter);
		
		log = Logger.getLogger(loggerName);
		log.setUseParentHandlers(false);
		log.addHandler(fileLoggingHandler);
		log.addHandler(consoleLoggingHandler);
	}
	
	public Logger getLogger() {
		return log;
	}
	/**
	 * util function that sets level for all handlers
	 * 
	 * @param levelString a levelString, case insensitive e.g. "all", "FINE"
	 */
	public void setLevel(String levelString) {
		levelString = new String(levelString).toLowerCase();
		switch (levelString) {
		
		case "all": 
			setAllLevels(Level.ALL); break;
		case "finest": 
			setAllLevels(Level.FINEST); break;
		case "finer": 
			setAllLevels(Level.FINER); break;
		case "fine": 
			setAllLevels(Level.FINE); break;
		case "config": 
			setAllLevels(Level.CONFIG); break;
		case "info": 
			setAllLevels(Level.INFO); break;
		case "warning": 
			setAllLevels(Level.WARNING); break;
		case "severe": 
			setAllLevels(Level.SEVERE); break;
		case "off": 
			setAllLevels(Level.OFF); break;
		default:
			
		}
	}
	/**
	 * private function to be called by setLevel
	 * @param level
	 */
	private void setAllLevels(Level level) {
		this.log.setLevel(level);
		Handler handlers[] = this.log.getHandlers();
		for (Handler handler: handlers) {
			handler.setLevel(level);
		}
	}
	
	public static void main(String[] args) {
		// logger defined above
		try {
			LogSystem logSystem = new LogSystem();
			Logger logger = logSystem.getLogger();

			logSystem.setLevel("finest");
			System.out.println("level: " + logger.getLevel());

			logger.log(Level.FINEST, "FINEST msg");
			logger.log(Level.FINER, "FINER msg");
			logger.log(Level.FINE, "FINE msg");
			logger.log(Level.CONFIG, "CONFIG msg");
			logger.log(Level.INFO, "INFO msg");
			logger.log(Level.WARNING, "WARNING msg");
			logger.log(Level.SEVERE, "SEVERE msg");

			System.out.println("name: " + log.getName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
