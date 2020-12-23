import common.Base;
import common.Games;
import common.Receiver;
import common.Url;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MatchStatsTest extends Base {

	@Before public void setUp() {
		createChromeDriver();
		openUrl(Url.REZULTATI_COM);
	}

	@Test public void matchStats() throws IOException {

		Receiver receiver = new Receiver();

		receiver.collectMatch(Games.OVER_3_5, 4);
	}

	@After public void close() {
		quitDriver();
	}
}
