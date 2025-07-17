package com.nba.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.nba.automation.models.Team;
import com.nba.automation.utils.WaitHelper;
import com.nba.automation.utils.TestDataHelper;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for NBA Standings page
 * Handles interactions with team standings for both Eastern and Western conferences
 * URL: https://stats.nba.com/standings/
 */
public class StandingsPage extends BasePage {

    // Page URL
    private static final String STANDINGS_URL = "/standings/";

    // Locators for standings page elements
    private static final By PAGE_TITLE = By.cssSelector("h1, .page-title");
    private static final By STANDINGS_CONTAINER = By.cssSelector("div.Standings");

    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public StandingsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to standings page and wait for content to load
     */
    @Step("Navigate to NBA standings page")
    public void navigateToStandingsPage() {
        String fullUrl = config.getBaseUrl() + STANDINGS_URL;
        navigateTo(fullUrl);

    }
}