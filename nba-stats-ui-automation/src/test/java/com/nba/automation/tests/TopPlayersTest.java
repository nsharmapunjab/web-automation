package com.nba.automation.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nba.automation.pages.LeadersPage;
import com.nba.automation.pages.PlayerPage;
import com.nba.automation.models.Player;
import com.nba.automation.utils.DriverManager;
import io.qameta.allure.*;
import org.testng.asserts.SoftAssert;

import java.util.List;

/**
 * Test class for verifying top players statistics
 * Compares player stats between leaders page and individual player pages
 */
@Epic("NBA Stats Verification")
@Feature("Player Statistics")
public class TopPlayersTest extends BaseTest {

    /**
     * Test to verify top 3 players' stats consistency
     * Compares PPG, APG, and RPG between leaders page and player pages
     */
    @Test(description = "Verify top 3 players stats match between leaders page and individual player pages")
    @Story("Top Players Stats Verification")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTop3PlayersStatsConsistency() {

        SoftAssert sa = new SoftAssert();
        // Step 1: Navigate to leaders page and get top 3 players
        LeadersPage leadersPage = new LeadersPage(DriverManager.getDriver());
        leadersPage.navigateToLeadersPage();

        // Get top 3 players with combined stats
        List<Player> top3PlayersFromLeaders = leadersPage.getTop3PlayersWithAllStats();

        Assert.assertFalse(top3PlayersFromLeaders.isEmpty(), "No players found on leaders page");
        Assert.assertTrue(top3PlayersFromLeaders.size() >= 3, "Less than 3 players found on leaders page");

        // Step 2: Verify each player's stats on their individual page
        PlayerPage playerPage = new PlayerPage(DriverManager.getDriver());

        for (int i = 0; i < Math.min(3, top3PlayersFromLeaders.size()); i++) {

            Player leaderPlayer = top3PlayersFromLeaders.get(i);

            WebElement playerUrlLink = leadersPage.getPlayerPageUrlLink(leaderPlayer.getName());
            playerPage.navigateToPlayerPageWithLink(playerUrlLink);
        }

        System.out.println("Validating player stats.....");
        for (int i = 0; i < Math.min(3, top3PlayersFromLeaders.size()); i++) {
            Player leaderPlayer = top3PlayersFromLeaders.get(i);

            // Get player stats from individual page
            Player playerPageStats = playerPage.getPlayerStats(i, leaderPlayer);

            // Verify player name matches
            sa.assertEquals(playerPageStats.getName().replaceAll("\\s+", "").toUpperCase(),
                    leaderPlayer.getName().replaceAll("\\s+","").toUpperCase(),
                    "Player name mismatch for player " + (i + 1));

            // Verify PPG matches (with small tolerance for rounding)
            double ppgDifference = Math.abs(playerPageStats.getPointsPerGame() - leaderPlayer.getPointsPerGame());
            sa.assertTrue(ppgDifference < 0.1,
                    String.format("PPG mismatch for %s: Leaders page=%.1f, Player page=%.1f",
                            leaderPlayer.getName(), leaderPlayer.getPointsPerGame(), playerPageStats.getPointsPerGame()));

            // Verify APG matches (with small tolerance for rounding)
            double apgDifference = Math.abs(playerPageStats.getAssistsPerGame() - leaderPlayer.getAssistsPerGame());
            sa.assertTrue(apgDifference < 0.1,
                    String.format("APG mismatch for %s: Leaders page=%.1f, Player page=%.1f",
                            leaderPlayer.getName(), leaderPlayer.getAssistsPerGame(), playerPageStats.getAssistsPerGame()));

            // Verify RPG matches (with small tolerance for rounding)
            double rpgDifference = Math.abs(playerPageStats.getReboundsPerGame() - leaderPlayer.getReboundsPerGame());
            sa.assertTrue(rpgDifference < 0.1,
                    String.format("RPG mismatch for %s: Leaders page=%.1f, Player page=%.1f",
                            leaderPlayer.getName(), leaderPlayer.getReboundsPerGame(), playerPageStats.getReboundsPerGame()));

            // Add player stats to Allure report
            Allure.addAttachment("Player " + (i + 1) + " Stats Comparison",
                    String.format("Name: %s\nPPG: %.1f (Leaders) vs %.1f (Player Page)\nAPG: %.1f vs %.1f\nRPG: %.1f vs %.1f",
                            leaderPlayer.getName(),
                            leaderPlayer.getPointsPerGame(), playerPageStats.getPointsPerGame(),
                            leaderPlayer.getAssistsPerGame(), playerPageStats.getAssistsPerGame(),
                            leaderPlayer.getReboundsPerGame(), playerPageStats.getReboundsPerGame()));

            // Log success for this player
            System.out.println("✓ Stats verification passed for: " + leaderPlayer.getName());

        }

        sa.assertAll();
        System.out.println("✓ All top 3 players stats verification completed successfully");
    }
}