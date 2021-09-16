package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GameCalculator extends Base {

	public void calculateFullTime(Game game, int includeCriteria) throws IOException {

		String fileLocation = FileName.MATCHES_UNSORTED(game, includeCriteria);
		FileWriter fw = new FileWriter(fileLocation, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);

		List<WebElement> lastFiveResults = getDriver().findElements(Locators.scoreH2HMatches);
		List<Integer> results = new ArrayList<>();

		char home;
		char away;
		int intHome;
		int intAway;
		int finalResult;

		if (lastFiveResults.size() == 5 || lastFiveResults.size() == 4) {
			for (WebElement element : lastFiveResults) {
				if (element.getText().length() == 5) {

					home = element.getText().charAt(0);
					away = element.getText().charAt(4);

					intHome = Integer.parseInt(String.valueOf(home));
					intAway = Integer.parseInt(String.valueOf(away));

					finalResult = intHome + intAway;

					switch (game) {
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
				} else {
					String title = trimTitle();
					String fileLocationUnprocessed = FileName.MATCHES_UNPROCESSED;
					FileWriter fwUnprocessed = new FileWriter(fileLocationUnprocessed, true);
					BufferedWriter bwUnprocessed = new BufferedWriter(fwUnprocessed);
					PrintWriter outUnprocessed = new PrintWriter(bwUnprocessed);
					outUnprocessed.println(title);
					outUnprocessed.close();
				}
			}
		}
		if (results.size() >= includeCriteria) {
			String title = trimTitle();
			out.println(results.size() + " -> " + title);
			out.close();
		}
	}

	public void calculateHalfTime(Game game, int includeCriteria) throws IOException, InterruptedException {

		By firstHalfResultLocator = By.xpath("//div[contains(@class,'incidentsHeader')]//div[text()='1. poluvrijeme']//..//div[2]");
		By secondHalfResultLocator = By.xpath("//div[contains(@class,'incidentsHeader')]//div[text()='2. poluvrijeme']//..//div[2]");

		String fileLocation = FileName.MATCHES_UNSORTED(game, includeCriteria);
		FileWriter fw = new FileWriter(fileLocation, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);

		List<WebElement> lastFiveResults = getDriver().findElements(Locators.scoreH2HMatches);
		List<Integer> results = new ArrayList<>();

		char home;
		char away;
		int intHome;
		int intAway;
		int finalResult = 0;

		if (lastFiveResults.size() == 5 || lastFiveResults.size() == 4) {
			for (WebElement result : lastFiveResults) {
				if (result.getText().length() == 5) {

					home = result.getText().charAt(0);
					away = result.getText().charAt(4);

					intHome = Integer.parseInt(String.valueOf(home));
					intAway = Integer.parseInt(String.valueOf(away));

					finalResult = intHome + intAway;

				}
				if (finalResult > 1) {
					result.click();
					Thread.sleep(1000);
					switchToWindow();

					List<WebElement> firstHalfResult = getDriver().findElements(firstHalfResultLocator);
					List<WebElement> secondHalfResult = getDriver().findElements(secondHalfResultLocator);

					char firstHalfHome;
					char firstHalfAway;
					int intFirstHalfHome;
					int intFirstHalfAway;
					int finalFirstHalfResult;

					char secondHalfHome;
					char secondHalfAway;
					int intSecondHalfHome;
					int intSecondHalfAway;
					int finalSecondHalfResult;

					for (WebElement f : firstHalfResult) {
						firstHalfHome = f.getText().charAt(0);
						firstHalfAway = f.getText().charAt(4);
						intFirstHalfHome = Integer.parseInt(String.valueOf(firstHalfHome));
						intFirstHalfAway = Integer.parseInt(String.valueOf(firstHalfAway));

						finalFirstHalfResult = intFirstHalfHome + intFirstHalfAway;

						if (finalFirstHalfResult >= 1 && finalFirstHalfResult <= 3) {
							for (WebElement s : secondHalfResult) {
								secondHalfHome = s.getText().charAt(0);
								secondHalfAway = s.getText().charAt(4);
								intSecondHalfHome = Integer.parseInt(String.valueOf(secondHalfHome));
								intSecondHalfAway = Integer.parseInt(String.valueOf(secondHalfAway));

								finalSecondHalfResult = intSecondHalfHome + intSecondHalfAway;
								if (finalSecondHalfResult >= 1 && finalSecondHalfResult <= 3) {
									results.add(finalResult);
									closeLastOpenedWindow();
								}
							}
						}
					}
				} else {
					String title = trimTitle();
					String fileLocationUnprocessed = FileName.MATCHES_UNPROCESSED;
					FileWriter fwUnprocessed = new FileWriter(fileLocationUnprocessed, true);
					BufferedWriter bwUnprocessed = new BufferedWriter(fwUnprocessed);
					PrintWriter outUnprocessed = new PrintWriter(bwUnprocessed);
					outUnprocessed.println(title);
					outUnprocessed.close();
				}
			}
			if (results.size() >= includeCriteria) {
				String title = trimTitle();
				out.println(results.size() + " -> " + title);
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