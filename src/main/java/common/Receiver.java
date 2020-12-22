package common;

import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Receiver extends Base {

	GameCalculator gameCalculator = new GameCalculator();

	public Receiver prihvacam() throws InterruptedException {

		Thread.sleep(2000);
		getDriver().findElement(Locators.prihvacam).click();

		return this;
	}

	public boolean verifyNoMutualMatchesIsNotVisible() {

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.findElements(Locators.noMutualGames).size() != 0;
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		return exists;
	}

	private Receiver goToHeadToHeadTab() {

		waitForElementToBeClickable(Locators.headToHeadTab);
		getDriver().findElement(Locators.headToHeadTab).click();

		return this;
	}

	private void getLastFiveResultsAndCollect(Games game, int includeCriteria) throws IOException {

		if (!verifyNoMutualMatchesIsNotVisible()) {
			gameCalculator.calculate(game, includeCriteria);
		}
	}

	public void collectMatch(Games game, int includeCriteria) throws IOException {

		List<WebElement> allMatches = getDriver().findElements(Locators.unplayedMatches);

		for (WebElement all : allMatches) {
			all.click();
			switchToWindow();
			goToHeadToHeadTab();
			getLastFiveResultsAndCollect(game, includeCriteria);
			switchTab();
		}
		sortList(game, includeCriteria);
	}

	public void sortList(Games game, int includeCriteria) {

		try (Stream<String> lines = Files.lines(Paths.get(FileName.MATCHES_UNSORTED(game, includeCriteria)))) {
			int n = 0;
			List<String> sortedLines = lines.sorted(Comparator.comparing(line -> line.substring(n)))
					.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
			Files.write(Paths.get(FileName.MATCHES(game, includeCriteria)), sortedLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
