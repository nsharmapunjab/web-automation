package com.nba.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.nba.automation.models.Team;
import com.nba.automation.utils.WaitHelper;
import com.nba.automation.utils.TestDataHelper;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Page Object for NBA Team Stats page
 * Handles interactions with team statistics table
 */
public class TeamStatsPage extends BasePage {

    // Page URL
    private static final String TEAM_STATS_URL = "/teams/traditional/?sort=W_PCT&dir=-1";

    // Locators for team stats elements
    private static final By STATS_TABLE = By.cssSelector("div.Crom_container__C45Ti table");
    private static final By TABLE_ROWS = By.cssSelector("tbody tr");
    private static final By TEAM_NAME_CELL = By.cssSelector("td:nth-child(2) a");
    private static final By WINS_CELL = By.cssSelector("td:nth-child(4)");
    private static final By LOSSES_CELL = By.cssSelector("td:nth-child(5)");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public TeamStatsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to team stats page
     */
    @Step("Navigate to team stats page")
    public void navigateToTeamStatsPage() {
        String fullUrl = config.getBaseUrl() + TEAM_STATS_URL;
        navigateTo(fullUrl);

        // Wait for stats table to load
        WaitHelper.waitForElementToBeVisible(driver, STATS_TABLE);
    }

    /**
     * Get all teams from the stats table
     * @return List of Team objects with stats
     */
    @Step("Extract team data from stats table")
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();

        // Wait for table rows to be present
        List<WebElement> rows = WaitHelper.waitForElementsToBePresent(driver, TABLE_ROWS);

        for (WebElement row : rows) {
            try {
                // Extract team data from each row
                String teamName = row.findElement(TEAM_NAME_CELL).getText().trim();
                String winsText = row.findElement(WINS_CELL).getText().trim();
                String lossesText = row.findElement(LOSSES_CELL).getText().trim();

                // Parse numeric values
                int wins = Integer.parseInt(winsText);
                int losses = Integer.parseInt(lossesText);
                double winPercentage = (double) wins / (wins + losses);

                // Create team object and add to list
                String conference = TestDataHelper.determineConference(teamName);
                Team team = new Team(teamName, conference, wins, losses, winPercentage);
                teams.add(team);

            } catch (Exception e) {
                // Log error and continue with next row
                System.err.println("Error parsing team data from standings row, Invalid row");
            }
        }

        return teams;
    }

    @Step("Get teams by conference: {conference}")
    public HashMap<String, List<Team>> getTeamsByConferenceNames() {
        List<Team> allTeams = getAllTeams();
        List<Team> westernTeams = new ArrayList<>();
        List<Team> easternTeams = new ArrayList<>();
        HashMap<String, List<Team>> map = new HashMap<>();

        for (Team team : allTeams) {
            if (team.getConference().equalsIgnoreCase("Western")) {
                westernTeams.add(team);
            }
            if (team.getConference().equalsIgnoreCase("Eastern")) {
                easternTeams.add(team);
            }
        }
        map.put("Western", westernTeams);
        map.put("Eastern", easternTeams);

        return map;
    }
}