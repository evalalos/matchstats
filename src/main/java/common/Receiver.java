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

    public Receiver acceptCookies() throws InterruptedException {

        Thread.sleep(2000);
        getDriver().findElement(Locators.acceptCookies).click();

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

    private void getLastFiveResultsAndCollectFullTime(Game game, int includeCriteria) throws IOException {

        if (!verifyNoMutualMatchesIsNotVisible()) {
            gameCalculator.calculateFullTime(game, includeCriteria);
        }
    }

    private void getLastFiveResultsAndCollectHalfTime(Game game, int includeCriteria) throws IOException, InterruptedException {

        if (!verifyNoMutualMatchesIsNotVisible()) {
            gameCalculator.calculateHalfTime(game, includeCriteria);
        }
    }

    public void collectFullTimeMatch(Game game, int includeCriteria) throws IOException, InterruptedException {

        acceptCookies();
        List<WebElement> allMatches = getDriver().findElements(Locators.unplayedMatches);

        for (WebElement all : allMatches) {
            all.click();
            switchToWindow();
            goToHeadToHeadTab();
            getLastFiveResultsAndCollectFullTime(game, includeCriteria);
            switchTab();
        }
        sortList(game, includeCriteria);
    }

    public void collectHalfTimeMatch(Game game, int includeCriteria) throws IOException, InterruptedException {

        acceptCookies();
        List<WebElement> allMatches = getDriver().findElements(Locators.unplayedMatches);

        for (WebElement all : allMatches) {
            all.click();
            switchToWindow();
            Thread.sleep(500);
            goToHeadToHeadTab();
            Thread.sleep(500);
            getLastFiveResultsAndCollectHalfTime(game, includeCriteria);
            Thread.sleep(2000);
            switchTab();
            Thread.sleep(1000);
        }
        sortList(game, includeCriteria);
    }

    public void sortList(Game game, int includeCriteria) {

        try (Stream<String> lines = Files.lines(Paths.get(FileName.MATCHES_UNSORTED(game, includeCriteria)))) {
            int n = 0;
            List<String> sortedLines = lines.sorted(Comparator.comparing(line -> line.substring(n)))
                    .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            Files.write(Paths.get(FileName.MATCHES(game, includeCriteria)), sortedLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collectMatch(Game game, int includeCriteria) throws IOException, InterruptedException {
        acceptCookies();
        List<WebElement> allMatches = getDriver().findElements(Locators.unplayedMatches);

        for (WebElement all : allMatches) {
            all.click();
            switchToWindow();
            Thread.sleep(500);
            goToHeadToHeadTab();
            Thread.sleep(500);
            if (game != Game.MINIMUM_ONE_GOAL_BOTH_HALF && game != Game.BETWEEN_1_3_BOTH_HALF) {
                getLastFiveResultsAndCollectFullTime(game, includeCriteria);
            } else {
                getLastFiveResultsAndCollectHalfTime(game, includeCriteria);
            }
            Thread.sleep(2000);
            switchTab();
            Thread.sleep(1000);
        }
        sortList(game, includeCriteria);
    }
}
