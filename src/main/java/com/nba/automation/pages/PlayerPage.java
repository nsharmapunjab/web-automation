package com.nba.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.nba.automation.models.Player;
import com.nba.automation.utils.WaitHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;

/**
 * Page Object for individual NBA Player pages
 * Handles interactions with player statistics and information
 */
public class PlayerPage extends BasePage {

    // Locators for player page elements
    private static final By PLAYER_NAME_HEADER = By.cssSelector("h1.PlayerSummary_playerNameText___MhqC");
    private static final By PLAYER_TEAM_INFO = By.cssSelector("h1.PlayerSummary_playerNameText___MhqC");
    private static final By TRADITIONAL_STATS_TABLE = By.cssSelector("table.Crom_table__p1iZz");
    private static final By STATS_ROWS = By.cssSelector("tbody tr");
    private static final By POINTS_CELL = By.cssSelector("td:nth-child(4)"); // PPG column
    private static final By ASSISTS_CELL = By.cssSelector("td:nth-child(17)"); // APG column
    private static final By REBOUNDS_CELL = By.cssSelector("td:nth-child(16)"); // RPG column

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public PlayerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to specific player page
     * @param playerUrl Player page URL
     */
    @Step("Navigate to player page: {playerUrl}")
    public void navigateToPlayerPage(String playerUrl) {
        navigateTo(playerUrl);

        // Wait for player name header to load
        WaitHelper.waitForElementToBeVisible(driver, PLAYER_NAME_HEADER);
    }

    @Step("Navigate to player page: {playerUrl}")
    public void navigateToPlayerPageWithLink(WebElement playerUrlLink) {

        By closePopup = By.xpath("//*[contains(@id,'close')]");
        clickIfPresent(closePopup);

        String playerUrl = playerUrlLink.getAttribute("href");
        ((JavascriptExecutor)driver).executeScript("window.open(arguments[0], '_blank')", playerUrl);

        System.out.println("Player link opened successfully");
    }

    /**
     * Wait for stats section to load completely
     * @return Time taken to load stats section in milliseconds
     */
    @Step("Wait for stats section to load")
    public long waitForStatsToLoad() {
        long startTime = System.currentTimeMillis();

        // Wait for traditional stats table to load
        WaitHelper.waitForElementToBeVisible(driver, TRADITIONAL_STATS_TABLE);

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Get player name from header
     * @return Player name
     */
    @Step("Get player name from header")
    public String getPlayerName() {
        WebElement nameHeader = WaitHelper.waitForElementToBeVisible(driver, PLAYER_NAME_HEADER);
        return nameHeader.getText().trim();
    }

    /**
     * Get player team from info section
     * @return Player team name
     */
    @Step("Get player team information")
    public String getPlayerTeam() {
        WebElement teamInfo = WaitHelper.waitForElementToBeVisible(driver, PLAYER_TEAM_INFO);
        return teamInfo.getText().trim();
    }

    /**
     * Get player statistics from traditional stats table
     * @return Player object with current season stats
     */
    @Step("Extract player statistics from stats table")
    public Player getPlayerStats(int i, Player leaderPlayer) {

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        for (int j=1;j<=3;j++) {
            driver.switchTo().window(tabs.get(j));
            if (driver.getTitle().contains(leaderPlayer.getName())) {
                break;
            }
            else {
                continue;
            }
        }

        // Wait for stats table to be visible
        WaitHelper.waitForElementToBeVisible(driver, TRADITIONAL_STATS_TABLE);

        Player player = new Player();
        player.setName(getPlayerName());
        player.setTeam(getPlayerTeam());

        try {
            // Get stats table
            WebElement statsTable = driver.findElement(TRADITIONAL_STATS_TABLE);

            // Get the first row (current season stats)
            WebElement firstRow = statsTable.findElement(STATS_ROWS);

            // Extract statistical values
            String pointsText = firstRow.findElement(POINTS_CELL).getText().trim();
            String assistsText = firstRow.findElement(ASSISTS_CELL).getText().trim();
            String reboundsText = firstRow.findElement(REBOUNDS_CELL).getText().trim();

            // Parse and set stats
            player.setPointsPerGame(Double.parseDouble(pointsText));
            player.setAssistsPerGame(Double.parseDouble(assistsText));
            player.setReboundsPerGame(Double.parseDouble(reboundsText));

        } catch (Exception e) {
            System.err.println("Error parsing player stats: " + e.getMessage());
        }

        return player;
    }

}