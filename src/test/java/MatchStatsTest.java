import common.Base;
import common.Receiver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MatchStatsTest extends Base {

	private static final String URL = "https://rezultati.com";
	private static final String TXT_FILE_PATH = "/Users/milos/Desktop/matches.txt/";

	@Before
	public void setUp(){
		createChromeDriver();
		openUrl(URL);
	}

	@Test
	public void matchStats() throws IOException {

		Receiver receiver = new Receiver();

		receiver.collectMatch(TXT_FILE_PATH);
	}

	@After
	public void close(){
		quitDriver();
	}
}
