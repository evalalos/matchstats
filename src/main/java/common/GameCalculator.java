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

	public void calculate(Games games) throws IOException {

		String fileLocation = "target/matches.txt";
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
			case OVER_OR_EQUAL_3:
				if (finalResult >= 3) {
					results.add(finalResult);
				}
				break;
			case UNDER_OR_EQUAL_2:
				if (finalResult <= 2) {
					results.add(finalResult);
				}
				break;
			case BOTH_TO_SCORE:
				if (intHome >= 1 && intAway >= 1) {
					results.add(finalResult);
				}
				break;
			case BOTH_TO_SCORE_AND_OVER_OR_EQUAL_3:
				if (intHome >= 1 && intAway >= 1 && finalResult >= 3) {
					results.add(finalResult);
				}
				break;
			case OVER_OR_EQUAL_4:
				if (finalResult >= 4) {
					results.add(finalResult);
				}
			}

			if (results.size() >= 3) {
				String title = trimTitle();
				out.println(title);
				out.close();
			}
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