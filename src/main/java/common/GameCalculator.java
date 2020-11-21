package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GameCalculator extends Base {

	public void calculate(Games games, int includeCriteria) throws IOException {

		String fileLocation = FileName.MATCHES_UNSORTED;
		FileWriter fw = new FileWriter(fileLocation, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);

		List<WebElement> lastFiveResults = getDriver().findElements(Locators.scoreLastFive);
		List<Integer> results = new ArrayList<>();

		char home;
		char away;
		int intHome;
		int intAway;
		int finalResult;

		for (WebElement element : lastFiveResults) {

			home = element.getText().charAt(0);
			away = element.getText().charAt(4);

			intHome = Integer.parseInt(String.valueOf(home));
			intAway = Integer.parseInt(String.valueOf(away));

			finalResult = intHome + intAway;

			switch (games) {
			case OVER_1_5:
				if (finalResult > 1.5) {
					results.add(finalResult);
				}
				break;
			case BETWEEN_2_AND_3:
				if (finalResult >= 2 && finalResult <= 3) {
					results.add(finalResult);
				}
				break;
			case OVER_2_5:
				if (finalResult > 2.5) {
					results.add(finalResult);
				}
				break;
			case UNDER_2_5:
				if (finalResult < 2.5) {
					results.add(finalResult);
				}
				break;
			case BOTH_TO_SCORE:
				if (intHome >= 1 && intAway >= 1) {
					results.add(finalResult);
				}
				break;
			case BOTH_TO_SCORE_AND_OVER_2_5:
				if (intHome >= 1 && intAway >= 1 && finalResult > 2.5) {
					results.add(finalResult);
				}
				break;
			case OVER_3_5:
				if (finalResult > 3.5) {
					results.add(finalResult);
				}
				break;
			case OVER_4_5:
				if (finalResult > 4.5) {
					results.add(finalResult);
				}
				break;
			case OVER_5_5:
				if (finalResult > 5.5) {
					results.add(finalResult);
				}
				break;
			case OVER_6_5:
				if (finalResult > 6.5) {
					results.add(finalResult);
				}
				break;
			}
		}
		if (results.size() >= includeCriteria) {
			String title = trimTitle();
			out.println(results.size() + " -> " + title);
			out.close();
		}
	}

	private String trimTitle() {

		String title = getDriver().getTitle();
		int pipe = title.indexOf("|") + 2;
		String newTitle = title.substring(pipe);

		String finalTitle = StringUtils.substring(newTitle, 0, newTitle.length() - 7);

		return finalTitle.trim();
	}
}