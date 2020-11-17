package common;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Receiver extends Base {

	public static final By allMatchesElements = By
			.xpath("//div[@class='sportName soccer']//div[@class='event__participant event__participant--home']");

	public static final By headToHeadTab = By.xpath("//div[@id='detail-bookmarks']//li[@id='li-match-head-2-head']");

	public static final By scoreLastFive = By
			.xpath("//table[@class='head_to_head h2h_mutual']//tbody//tr[@class='odd highlight' or @class='even "
					+ "highlight'][not(ancestor::div[contains(@style, 'display: none;')])"
					+ "]//td//span[@class='score']//strong");

	public static final By noMutualGames = By
			.xpath("//table[@class='head_to_head h2h_mutual']//tbody//tr//td[text()='Nema igara u tijeku.'][not"
					+ "(ancestor::div[contains(@style, 'display: none;')])]");

	public static final By prikaziJosMeceva = By.xpath("//table[@class='head_to_head "
			+ "h2h_mutual']//tbody//tr//td//a[text()='Prikaži još mečeva '][not(ancestor::div[contains(@style, "
			+ "'display:"
			+ " none;')])]");

	public static final By prihvacam = By.xpath("//button[text()='Prihvaćam']");

	public Receiver prihvacam() throws InterruptedException {

		Thread.sleep(2000);
		getDriver().findElement(prihvacam).click();

		return this;
	}

	public boolean verifyNoMutualMatchesIsNotVisible() {


		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.findElements(noMutualGames).size() != 0;
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		return exists;
	}

	public boolean verifyPrikaziJosMecevaIsVisible() {

		boolean isVisible = true;

		try {
			getDriver().findElement(prikaziJosMeceva).isDisplayed();
		} catch (NoSuchElementException ignore) {
			isVisible = false;
		}

		return isVisible;
	}

	private Receiver goToHeadToHeadTab() {

		waitForElementToBeClickable(headToHeadTab);
		getDriver().findElement(headToHeadTab).click();

		return this;
	}

	private void getLastFiveResultsAndCollect(String filePath) throws IOException {

		FileWriter fw = new FileWriter(filePath, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);

		if (!verifyNoMutualMatchesIsNotVisible()) {
			List<WebElement> lastFiveResults = getDriver().findElements(scoreLastFive);
			List<Integer> results = new ArrayList<>();

			for (WebElement element : lastFiveResults) {
				char home = element.getText().charAt(0);
				char away = element.getText().charAt(4);

				int intHome = Integer.parseInt(String.valueOf(home));
				int intAway = Integer.parseInt(String.valueOf(away));

				int finalResult = intHome + intAway;

				if (finalResult >= 3) {
					results.add(finalResult);
				}
			}

			if (results.size() >= 2) {
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

	public Receiver collectMatch(String filePath) throws IOException {

		List<WebElement> allMatches = getDriver().findElements(allMatchesElements);

		for (WebElement all : allMatches) {
			all.click();
			switchToWindow();
			goToHeadToHeadTab();
			getLastFiveResultsAndCollect(filePath);
			switchTab();
		}

		return this;
	}
}
