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
 * A wrapper for the default JRE logger library.
 * What it adds is the ability to create customLevel, print only that level, or print that level + all default levels 
 * e.g. consider these series of logging calls
 * 
 * 1. log("dev_boyang", "i'm boyang and i'm debugging this");
 * 2. log("dev_minqi", "hey i'm minqi but I don't want to see boyang's stupid debug msg")
 * 3. log("info", "you all shut up. I'm one of the default level and I'm printing presumebly impt stuff. want to see or not?");
 * 
 * LogSystem allows printing only 1, only 2, only 3, 1 and 3, 2 and 3
 * 
 * @usage
 * See below code for usage
 * 
			// LogSystem.init(myLoggerName, myLogFileName);
			
			LogSystem.log("info", "1. I'm printing an info");
			
			LogSystem.setLevel("warning");
			LogSystem.log("info", "See this time info no longer output, since the loglevel is higher");
			
			LogSystem.setLevel("BoyangDebug");
			LogSystem.log("BoyangDebug", "2. Now that custom level is set, the matching on will be ouput");
			LogSystem.log("Laoma_debug", "but not the unmatching ones!");
			LogSystem.log("finest", "3. It output the default level logs as well, since customlevels are considered one level lower than finest. Want to turn them off? Read on");
			
			LogSystem.setLevelEqual("BoyangFocusOnDebug");
			LogSystem.log("warning", "now even the warnings are not output");
 			LogSystem.log("BoyangFocusOnDebug", "4. only the matching log");
 			
 			Logger logger = LogSystem.getLogger(); // tweak it more by getting the logger obj
 * 
 * 
 * 
 *  
 * @category
 * The following levels taken from Java lib:
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
 *  @see
 *  Also util functions:
 *  setLevel(levelString) to set all handler to a certain level
 *  setLevelEqual(customLevelString) to enter equal mode and set customLevel
 *  getLogger() returns the logger obj
 */
public final class LogSystem {
	
	private static Logger log; 
	private static String defaultLogFileName = "log.txt";
	private static String defaultLoggerName = "LogSystemLogger"; 
	private static String customLevel = null;
	private static boolean isEqualMode = false;
	private static boolean isInitialized = false;
	
	private LogSystem() {
	
	}
	
	/**
	 * open log file, set formatter and handler, under the hood stuff
	 * @param loggerName
	 * @param logFileName
	 * @throws IOException
	 */
	public static void init(String loggerName, String logFileName) throws IOException {
		if (!isInitialized) {
			isInitialized = true;
			Handler fileLoggingHandler = new FileHandler(logFileName);
			Handler consoleLoggingHandler = new ConsoleHandler();

			Formatter formatter = new SimpleFormatter();
			fileLoggingHandler.setFormatter(formatter);
			consoleLoggingHandler.setFormatter(formatter);

			log = Logger.getLogger(loggerName);
			log.setUseParentHandlers(false);
			log.addHandler(fileLoggingHandler);
			log.addHandler(consoleLoggingHandler);
			setLevel("config");
		}
	}
	
	public static Logger getLogger() throws IOException {
		if (!isInitialized) {
			init(defaultLoggerName, defaultLogFileName);
		}
		return log;
	}
	
	/**
	 * util function that sets level for all handlers
	 * 
	 * In this case, 
	 * 1. if the levelString can be recognized as one of the default levels, 
	 * just set greater than or equal to, like the default behaviour
	 * 
	 * 2. If given a custom levelString, It's by default the lowest level 
	 * (i.e. all log msg that either are of default level, or match this custom level, will be output) 
	 * *the past tense and past participle are and only are OUTPUT! Ouputted is a particularly ugly reminiscence that should be annihilated for good
	 *   
	 * In this sense, the logger is still in the default "greater than or equal" mode, with the custom level been
	 * recognized as the highest level below Level.FINEST
	 * 
	 * @param levelString a levelString, case insensitive, don't give me space lah! e.g. "all", "FINE", "MENGYUE_DEBUGGING"
	 */
	public static void setLevel(String levelString) throws IOException {
		if (!isInitialized) {
			init(defaultLoggerName, defaultLogFileName);
		}
		isEqualMode = false; // Whenever you call setLevel, out of equalMode already
		Level level = stringToLevel(levelString);
		
		if (level == null){ // It's custom levelString. Set to FINEST first
			setLevel("FINEST");
			customLevel = levelString;
		}
		else { // remember to go back to the normal mode, and unset customLevel
			customLevel = null;
			setAllLevel(level);
		}
	}
	/**
	 * This is the special mode that default JRE library is not providing
	 * "I want to output only this particular level"
	 * When set, isEqualMode is true and only the custom level log msg will be ouput
	 * 
	 * A interesting case emerges when a customLevelString that's identical to one of the default is stored
	 * In this case, the logger can only be in equalMode
	 * Therefore, it will compare the strings. Apparently only the level, represented by the stored levelString, will be output
	 * 
	 * @param levelString a levelString, case insensitive, don't give me space lah! e.g. "all", "FINE", "MENGYUE_IS_DEBUGGING" etc.
	 */
	public static void setLevelEqual(String customLevelString) throws IOException {
		if (!isInitialized) {
			init(defaultLoggerName, defaultLogFileName);
		}
		setAllLevel(Level.FINEST);
		isEqualMode = true;
		customLevel = customLevelString;
	}
	
	/**
	 * Given a levelString, return the Level object
	 * @param levelString
	 * @return return the Level obj. If no match, return null (likely indicates a custom log level, represented by a string)
	 */
	private static Level stringToLevel(String levelString) throws IOException {
		if (!isInitialized) {
			init(defaultLoggerName, defaultLogFileName);
		}
		Level level;
		levelString = new String(levelString).toLowerCase();
		
		switch (levelString) {
		
		case "all": 
			level = (Level.ALL); break;
		case "finest": 
			level = (Level.FINEST); break;
		case "finer": 
			level = (Level.FINER); break;
		case "fine": 
			level = (Level.FINE); break;
		case "config": 
			level = (Level.CONFIG); break;
		case "info": 
			level = (Level.INFO); break;
		case "warning": 
			level = (Level.WARNING); break;
		case "severe": 
			level = (Level.SEVERE); break;
		case "off": 
			level = (Level.OFF); break;
		default:
			level = null;
		}
		
		return level;
	}
	
	/**
	 * private function to be called by setLevel
	 * iterate through all handlers and set their levels
	 * @param level
	 */
	private static void setAllLevel(Level level) throws IOException {
		if (!isInitialized) {
			init(defaultLoggerName, defaultLogFileName);
		}
		LogSystem.log.setLevel(level);
		Handler handlers[] = LogSystem.log.getHandlers();
		for (Handler handler: handlers) {
			handler.setLevel(level);
		}
	}
	
	/**
	 * Just log it!
	 * @param levelString
	 * @param msg
	 */
	public static void log(String levelString, String msg) throws IOException {
		if (!isInitialized) {
			init(defaultLoggerName, defaultLogFileName);
		}
		if (isEqualMode) {
			if (levelString.equals(customLevel)) {
				log.log(log.getLevel(), msg);
			}
		}
		else {
			// Okay not in equalMode. Is your levelString a custom one?
			Level level = stringToLevel(levelString);
			if (level != null) {
				log.log(level, msg);
			}
			else { // Okay you give me customLevel while not in equalMode. Does it match customLevel?
				if (levelString.equals(customLevel)) {
					log.log(log.getLevel(), msg);
				}
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		// logger defined above
		// test this in JUnit my friend!
		String info = new StringBuilder()
						.append("LogSystem\n\n")
						.append("A wrapped logger that allows devs to use custom logging levels and logging behaviours.\n")
						.append("By Boyang\n")
						.toString();
		System.out.println(info);
		
		// minimal MSS example
		try {
			// LogSystem.init(myLoggerName, myLogFileName);
			
			LogSystem.log("info", "1. I'm printing an info");
			
			LogSystem.setLevel("warning");
			LogSystem.log("info", "See this time info no longer output, since the loglevel is higher");
			
			LogSystem.setLevel("BoyangDebug");
			LogSystem.log("BoyangDebug", "2. Now that custom level is set, the matching on will be ouput");
			LogSystem.log("Laoma_debug", "but not the unmatching ones!");
			LogSystem.log("finest", "3. It output the default level logs as well, since customlevels are considered one level lower than finest. Want to turn them off? Read on");
			
			LogSystem.setLevelEqual("BoyangFocusOnDebug");
			LogSystem.log("warning", "now even the warnings are not output");
			LogSystem.log("BoyangFocusOnDebug", "4. only the matching log");
			
			Logger logger = LogSystem.getLogger(); // tweak it more by getting the logger obj
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
