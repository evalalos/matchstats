import common.Base;
import common.Receiver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MatchStatsTest extends Base {

	@Before
	public void setUp(){
		createChromeDriver();

		openUrl("https://rezultati.com");
	}

	@Test
	public void milos() throws IOException, InterruptedException {

		Receiver receiver = new Receiver();

		receiver
				//.prihvacam()
				.collectMatch("/Users/milos/Desktop/matches.txt/");

	}

	@After
	public void close(){
		quitDriver();
	}
}
