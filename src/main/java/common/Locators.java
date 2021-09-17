package common;

import org.openqa.selenium.By;

public class Locators {

	public static final By unplayedMatches = By
			.xpath("//div[@class='event__time']");

	public static final By headToHeadTab = By.xpath("//a[@class='tabs__tab'][text()='Omjer']");

	public static final By scoreH2HMatches = By
			.xpath("//div[text()='Međusobni mečevi']//..//span[contains(@class,'regularTimeResult')]");

	public static final By noMutualGames = By
			.xpath("//table[@class='head_to_head h2h_mutual']//tbody//tr//td[text()='Nema igara u tijeku.'][not"
					+ "(ancestor::div[contains(@style, 'display: none;')])]");

	public static final By prikaziJosMeceva = By.xpath("//table[@class='head_to_head "
			+ "h2h_mutual']//tbody//tr//td//a[text()='Prikaži još mečeva '][not(ancestor::div[contains(@style, "
			+ "'display:"
			+ " none;')])]");

	public static final By acceptCookies = By.xpath("//button[@id='onetrust-accept-btn-handler']");

	public static final By firstHalfResult = By.xpath("//div[contains(@class,'incidentsHeader')]//div[text()='1. poluvrijeme']//..//div[2]");

	public static final By secondHalfResult = By.xpath("//div[contains(@class,'incidentsHeader')]//div[text()='2. poluvrijeme']//..//div[2]");
}
