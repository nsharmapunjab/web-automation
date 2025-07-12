package com.nba.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.nba.automation.pages.LeadersPage;
import com.nba.automation.pages.PlayerPage;
import com.nba.automation.models.Player;
import com.nba.automation.utils.DriverManager;
import com.nba.automation.config.ConfigManager;
import io.qameta.allure.*;

import java.util.List;

/**
 * Test class for verifying page performance
 * Tests loading time of player pages and stats sections
 */
@Epic("NBA Stats Performance")
@Feature("Page Load Performance")
public class PerformanceTest extends BaseTest {

    private ConfigManager config = ConfigManager.getInstance();

    /**
     * Test to verify player page stats section loads within performance threshold
     * Checks loading time for top 3 players' stats sections
     */
    @Test(description = "Verify player page stats section loads within 4 seconds")
    @Story("Player Page Performance")
    @Severity(SeverityLevel.NORMAL)
    public void verifyPlayerPageStatsLoadingPerformance() {
        // Get performance threshold from configuration
        int performanceThreshold = config.getPerformanceThreshold(); // 4000ms

        // Step 1: Get top 3 players from leaders page
        LeadersPage leadersPage = new LeadersPage(DriverManager.getDriver());
        leadersPage.navigateToLeadersPage();

        List<Player> top3Players = leadersPage.getTop3PlayersWithAllStats();
        Assert.assertFalse(top3Players.isEmpty(), "No players found for performance testing");

        // Step 2: Test loading performance for each player page
        PlayerPage playerPage = new PlayerPage(DriverManager.getDriver());

        Player player = top3Players.get(0);

        // Get player page URL
        String playerUrl = leadersPage.getPlayerPageUrl(player.getName());
        Assert.assertNotNull(playerUrl, "Player page URL not found for: " + player.getName());

        // Measure page navigation time
        long navigationStartTime = System.currentTimeMillis();
        playerPage.navigateToPlayerPage(playerUrl);
        long navigationEndTime = System.currentTimeMillis();
        long navigationTime = navigationEndTime - navigationStartTime;
        System.out.println("Navigation time : " + navigationTime);

        // Measure stats section loading time
        long statsLoadTime = playerPage.waitForStatsToLoad();
        System.out.println("StatsLoad time : " + statsLoadTime);

        // Calculate total loading time
        long totalLoadTime = navigationTime + statsLoadTime;

        // Verify performance threshold
        Assert.assertTrue(statsLoadTime <= performanceThreshold,
                String.format("Stats section loading time exceeded threshold for %s: %dms > %dms",
                        player.getName(), statsLoadTime, performanceThreshold));

        // Add performance data to Allure report
        Allure.addAttachment("Performance Data - " + player.getName(),
                String.format("Navigation Time: %dms\nStats Load Time: %dms\nTotal Time: %dms\nThreshold: %dms",
                        navigationTime, statsLoadTime, totalLoadTime, performanceThreshold));

        // Log performance results
        System.out.println(String.format("✓ Performance test passed for %s: %dms (threshold: %dms)",
                player.getName(), statsLoadTime, performanceThreshold));

        System.out.println("✓ Player page performance tests completed successfully");
    }
}