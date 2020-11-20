package common;

import org.openqa.selenium.By;

public class Locators {

	public static final By unplayedMatches = By
			.xpath("//div[@class='sportName soccer']//div[@class='event__time']");

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
}
