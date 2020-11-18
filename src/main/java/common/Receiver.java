package common;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	private void getLastFiveResultsAndCollect(Games games, int includeCriteria) throws IOException {

		if (!verifyNoMutualMatchesIsNotVisible()) {
			gameCalculator.calculate(games, includeCriteria);
		}
	}

	public void collectMatch(Games games, int includeCriteria) throws IOException {

		List<WebElement> allMatches = getDriver().findElements(Locators.allMatchesElements);

		for (WebElement all : allMatches) {
			all.click();
			switchToWindow();
			goToHeadToHeadTab();
			getLastFiveResultsAndCollect(games, includeCriteria);
			switchTab();
		}
	}
}
