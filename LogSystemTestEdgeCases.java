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

public class LogSystemTestEdgeCases {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FileOutputStream flush = new FileOutputStream("testLog.txt");
		flush.write((new String()).getBytes());
		flush.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		FileOutputStream flush = new FileOutputStream("testLog.txt");
		flush.write((new String()).getBytes());
		flush.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEdgeCases() {
		// edge cases
		try {
			LogSystem.init("testLogger", "testLog.txt");
			
			LogSystem.log("", "false");
			LogSystem.log(null, "false");
			LogSystem.log("config", ""); // shouldn't be logged, I specify
			LogSystem.log("config", null); // shouldn't be logged
			// All the above print warnings
			
			BufferedReader br = new BufferedReader(new FileReader("testLog.txt"));
			int count = 0;
			String line;
			while ((line = br.readLine()) != null) {
				count++;
				assertFalse(line.contains("false"));
			}
			assertEquals(count, 8);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
