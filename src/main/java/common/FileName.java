package common;

public class FileName {

	public static String MATCHES_UNSORTED(Game game, int includeCriteria) {
		return String.format("target/unsorted_%s_%sod5.txt.txt", game, includeCriteria);
	}

	public static String MATCHES(Game game, int includeCriteria) {
		return String.format("target/%s_%sod5.txt", game, includeCriteria);
	}

	public static final String MATCHES_UNPROCESSED = "target/matches_unprocessed.txt";
}
