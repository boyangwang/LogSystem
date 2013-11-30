/**
 * 
 */
package boyang.logsystem;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * 
 * @author boyang
 *
 */
public class LogSystemTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FileOutputStream flush = new FileOutputStream("testLog.txt");
		flush.write((new String()).getBytes());
		flush.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FileOutputStream flush = new FileOutputStream("testLog.txt");
		flush.write((new String()).getBytes());
		flush.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

//	/**
//	 * Test method for {@link boyang.logsystem.LogSystem#init(java.lang.String, java.lang.String)}.
//	 */
//	@Test
//	public void testInit() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link boyang.logsystem.LogSystem#getLogger()}.
//	 */
//	@Test
//	public void testGetLogger() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link boyang.logsystem.LogSystem#setLevel(java.lang.String)}.
//	 */
//	@Test
//	public void testSetLevel() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link boyang.logsystem.LogSystem#setLevelEqual(java.lang.String)}.
//	 */
//	@Test
//	public void testSetLevelEqual() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link boyang.logsystem.LogSystem#log(java.lang.String, java.lang.String)}.
	 * How I even test write to file effect?
	 * I count # of lines (= # of logs * 2) and # of false (= 0)
	 */
	@Test
	public void testLog() {
		try {
			
			LogSystem.init("testLogger", "testLog.txt");
			assertEquals("testLogger", LogSystem.getLogger().getName());
			
			// at the beginning, the logger is not in equalMode and level is config
			LogSystem.log("off", "true");
			LogSystem.log("severe", "true");
			LogSystem.log("warning", "true");
			LogSystem.log("info", "true");
			LogSystem.log("config", "true");
			LogSystem.log("fine", "false");
			LogSystem.log("finer", "false");
			LogSystem.log("finest", "false");
			LogSystem.log("all", "false");
			LogSystem.log("customlevel", "false");
			
			// is setLevel working?
			LogSystem.setLevel("severe");
			
			LogSystem.log("off", "true");
			LogSystem.log("severe", "true");
			LogSystem.log("warning", "false");
			LogSystem.log("info", "false");
			LogSystem.log("config", "false");
			LogSystem.log("fine", "false");
			LogSystem.log("finer", "false");
			LogSystem.log("finest", "false");
			LogSystem.log("all", "false");
			LogSystem.log("customLevel", "false");
			
			// is setLevelEqual working?
			LogSystem.setLevelEqual("customLevel");
			
			LogSystem.log("off", "false");
			LogSystem.log("severe", "false");
			LogSystem.log("warning", "false");
			LogSystem.log("info", "false");
			LogSystem.log("config", "false");
			LogSystem.log("fine", "false");
			LogSystem.log("finer", "false");
			LogSystem.log("finest", "false");
			LogSystem.log("all", "false");
			LogSystem.log("customLevel", "true");
			LogSystem.log("anotherCustomLevel", "false");
			
			// special: setLevel using customLevel
			LogSystem.setLevel("customLevel");
			
			LogSystem.log("off", "true");
			LogSystem.log("severe", "true");
			LogSystem.log("warning", "true");
			LogSystem.log("info", "true");
			LogSystem.log("config", "true");
			LogSystem.log("fine", "true");
			LogSystem.log("finer", "true");
			LogSystem.log("finest", "true");
			LogSystem.log("all", "false");
			LogSystem.log("customLevel", "true");
			LogSystem.log("anotherCustomLevel", "false");
			
			// 17 true, 34 lines
			BufferedReader br = new BufferedReader(new FileReader("testLog.txt"));
			int count = 0;
			String line;
			while ((line = br.readLine()) != null) {
				count++;
				assertFalse(line.contains("false"));
			}
			assertEquals(count, 34);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
