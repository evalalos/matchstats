import common.Base;
import common.Game;
import common.Receiver;
import common.Url;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class MatchStatsTest extends Base {

    @Before
    public void setUp() {
        createChromeDriver();
        openUrl(Url.REZULTATI_COM);
    }

    @Test
    public void matchStats() throws IOException, InterruptedException {

        Receiver receiver = new Receiver();
        receiver.collectMatch(Game.MINIMUM_ONE_GOAL_BOTH_HALF, 4);
    }

    @After
    public void close() {
        quitDriver();
    }
}
