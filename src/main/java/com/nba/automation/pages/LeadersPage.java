package com.nba.automation.pages;

import org.openqa.selenium.*;
import com.nba.automation.models.Player;
import com.nba.automation.utils.WaitHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for NBA Leaders page
 * Handles interactions with player statistics leaders
 */
public class LeadersPage extends BasePage {

    // Page URL
    private static final String LEADERS_URL = "/leaders/";

    // Locators for leaders elements
    private static final By LEADERS_TABLE = By.xpath("//*[contains(@class,'nba-stats')]//*[contains(@class,'crom-container')]");
    private static final By PLAYER_ROWS = By.cssSelector("tbody tr");
    private static final By PLAYER_NAME_CELL = By.cssSelector("td:nth-child(2) a");
    private static final By PLAYER_TEAM_CELL = By.cssSelector("td:nth-child(3)");
    private static final By POINTS_STAT_VALUE_CELL = By.cssSelector("td:nth-child(6)");
    private static final By ASSISTS_STAT_VALUE_CELL = By.cssSelector("td:nth-child(19)");
    private static final By REBOUND_STAT_VALUE_CELL = By.cssSelector("td:nth-child(18)");
    private static final By PLAYER_LINK = By.cssSelector("td:nth-child(2) a");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public LeadersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to leaders page
     */
    @Step("Navigate to leaders page")
    public void navigateToLeadersPage() {
        String fullUrl = config.getBaseUrl() + LEADERS_URL;
        navigateToLeaderPage(fullUrl);

        // Wait for leaders tables to load
        WaitHelper.waitForElementToBeVisible(driver, LEADERS_TABLE);
        System.out.println("Leader Page loaded successfully...");
    }

    /**
     * Get top 3 players in points per game
     * @return List of top 3 players with PPG stats
     */
    @Step("Get top 3 players in points per game")
    public List<Player> getTop3Leaders() {
        return getTopPlayersFromTable(LEADERS_TABLE, 3);
    }

    /**
     * Get top players from specific statistics table
     * @param tableLocator Table locator
     * @param count Number of top players to get
     * @return List of top players
     */
    private List<Player> getTopPlayersFromTable(By tableLocator, int count) {
        List<Player> players = new ArrayList<>();

        // Wait for table to be visible
        WaitHelper.waitForElementToBeVisible(driver, tableLocator);

        // Get table rows
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(PLAYER_ROWS);

        // Limit to requested count
        int playersToProcess = Math.min(count, rows.size());

        for (int i = 0; i < playersToProcess; i++) {
            WebElement row = rows.get(i);
            try {
                // Extract player data from each row
                String playerName = row.findElement(PLAYER_NAME_CELL).getText().trim();
                String teamName = row.findElement(PLAYER_TEAM_CELL).getText().trim();
                String pointsStatValueText = row.findElement(POINTS_STAT_VALUE_CELL).getText().trim();
                String assistsStatValueText = row.findElement(ASSISTS_STAT_VALUE_CELL).getText().trim();
                String reboundStatValueText = row.findElement(REBOUND_STAT_VALUE_CELL).getText().trim();

                // Parse stat value
                double pointsStatValue = Double.parseDouble(pointsStatValueText);
                double assistsStatValue = Double.parseDouble(assistsStatValueText);
                double reboundStatValue = Double.parseDouble(reboundStatValueText);

                // Create player object with appropriate stat
                Player player = new Player();
                player.setName(playerName);
                player.setTeam(teamName);
                player.setPointsPerGame(pointsStatValue);
                player.setAssistsPerGame(assistsStatValue);
                player.setReboundsPerGame(reboundStatValue);

                players.add(player);

            } catch (Exception e) {
                // Log error and continue with next row
                System.err.println("Error parsing player data from leaders row: " + e.getMessage());
            }
        }

        return players;
    }

    public void clickIfPresent(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            element.click();
            System.out.println("Element clicked: " + locator);
        } catch (TimeoutException | NoSuchElementException e) {
            // Element not found within 3 seconds — safely ignore
            System.out.println("Element not found or not clickable: " + locator);
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Get combined top 3 players with all stats
     * This method combines data from all three leader tables
     * @return List of top 3 players with complete stats
     */
    @Step("Get top 3 players with combined statistics")
    public List<Player> getTop3PlayersWithAllStats() {

        System.out.println("Leadership Stats");

        List<Player> pointsLeaders = getTop3Leaders();

        // Create combined player objects (this assumes the same top 3 players)
        List<Player> combinedPlayers = new ArrayList<>();

        for (int i = 0; i < Math.min(3, pointsLeaders.size()); i++) {
            Player pointsPlayer = pointsLeaders.get(i);
            Player combinedPlayer = new Player();

            combinedPlayer.setName(pointsPlayer.getName());
            combinedPlayer.setTeam(pointsPlayer.getTeam());
            combinedPlayer.setPointsPerGame(pointsPlayer.getPointsPerGame());

            combinedPlayer.setAssistsPerGame(pointsPlayer.getAssistsPerGame());

            combinedPlayer.setReboundsPerGame(pointsPlayer.getReboundsPerGame());

            combinedPlayers.add(combinedPlayer);
        }

        By closePopup = By.xpath("//*[contains(@id,'close')]");
        clickIfPresent(closePopup);

        System.out.println("Loaded best players data successfully");

        return combinedPlayers;
    }

    /**
     * Get player page URL from leaders table
     * @param playerName Player name to find
     * @return Player page URL
     */
    @Step("Get player page URL for: {playerName}")
    public String getPlayerPageUrl(String playerName) {

        // Search for player in points leaders table first
        WebElement table = driver.findElement(LEADERS_TABLE);
        List<WebElement> rows = table.findElements(PLAYER_ROWS);

        for (WebElement row : rows) {
            try {
                WebElement nameCell = row.findElement(PLAYER_NAME_CELL);
                if (nameCell.getText().trim().equals(playerName)) {
                    WebElement playerLink = row.findElement(PLAYER_LINK);
                    return playerLink.getAttribute("href");
                }
            } catch (Exception e) {
                // Continue searching
            }
        }

        return null; // Player not found
    }

    @Step("Get player page URL for: {playerName}")
    public WebElement getPlayerPageUrlLink(String playerName) {

        // Search for player in points leaders table first
        WebElement table = driver.findElement(LEADERS_TABLE);
        List<WebElement> rows = table.findElements(PLAYER_ROWS);

        for (WebElement row : rows) {
            try {
                WebElement nameCell = row.findElement(PLAYER_NAME_CELL);
                if (nameCell.getText().trim().equals(playerName)) {
                    WebElement playerLink = row.findElement(PLAYER_LINK);
                    return playerLink;
                }
            } catch (Exception e) {
                // Continue searching
            }
        }

        return null; // Player not found
    }
}