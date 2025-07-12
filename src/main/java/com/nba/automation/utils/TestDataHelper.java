package com.nba.automation.utils;

import com.nba.automation.models.Team;
import com.nba.automation.models.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Test data helper utility class
 * Provides common test data and helper methods for NBA teams and players
 * Includes comprehensive team-to-conference mapping and test data validation
 */
public class TestDataHelper {

    // Static mapping of NBA teams to their conferences
    private static final Map<String, String> TEAM_CONFERENCE_MAP = new HashMap<>();

    // Alternative team name mappings for flexible matching
    private static final Map<String, String> TEAM_NAME_ALIASES = new HashMap<>();

    // Team abbreviations mapping
    private static final Map<String, String> TEAM_ABBREVIATIONS = new HashMap<>();

    static {
        initializeTeamConferenceMapping();
        initializeTeamAliases();
        initializeTeamAbbreviations();
    }

    /**
     * Initialize the complete NBA team-to-conference mapping
     * All 30 NBA teams mapped to their respective conferences
     */
    private static void initializeTeamConferenceMapping() {
        // Eastern Conference teams (15 teams)
        TEAM_CONFERENCE_MAP.put("Atlanta Hawks", "Eastern");
        TEAM_CONFERENCE_MAP.put("Boston Celtics", "Eastern");
        TEAM_CONFERENCE_MAP.put("Brooklyn Nets", "Eastern");
        TEAM_CONFERENCE_MAP.put("Charlotte Hornets", "Eastern");
        TEAM_CONFERENCE_MAP.put("Chicago Bulls", "Eastern");
        TEAM_CONFERENCE_MAP.put("Cleveland Cavaliers", "Eastern");
        TEAM_CONFERENCE_MAP.put("Detroit Pistons", "Eastern");
        TEAM_CONFERENCE_MAP.put("Indiana Pacers", "Eastern");
        TEAM_CONFERENCE_MAP.put("Miami Heat", "Eastern");
        TEAM_CONFERENCE_MAP.put("Milwaukee Bucks", "Eastern");
        TEAM_CONFERENCE_MAP.put("New York Knicks", "Eastern");
        TEAM_CONFERENCE_MAP.put("Orlando Magic", "Eastern");
        TEAM_CONFERENCE_MAP.put("Philadelphia 76ers", "Eastern");
        TEAM_CONFERENCE_MAP.put("Toronto Raptors", "Eastern");
        TEAM_CONFERENCE_MAP.put("Washington Wizards", "Eastern");

        // Western Conference teams (15 teams)
        TEAM_CONFERENCE_MAP.put("Dallas Mavericks", "Western");
        TEAM_CONFERENCE_MAP.put("Denver Nuggets", "Western");
        TEAM_CONFERENCE_MAP.put("Golden State Warriors", "Western");
        TEAM_CONFERENCE_MAP.put("Houston Rockets", "Western");
        TEAM_CONFERENCE_MAP.put("LA Clippers", "Western");
        TEAM_CONFERENCE_MAP.put("Los Angeles Lakers", "Western");
        TEAM_CONFERENCE_MAP.put("Memphis Grizzlies", "Western");
        TEAM_CONFERENCE_MAP.put("Minnesota Timberwolves", "Western");
        TEAM_CONFERENCE_MAP.put("New Orleans Pelicans", "Western");
        TEAM_CONFERENCE_MAP.put("Oklahoma City Thunder", "Western");
        TEAM_CONFERENCE_MAP.put("Phoenix Suns", "Western");
        TEAM_CONFERENCE_MAP.put("Portland Trail Blazers", "Western");
        TEAM_CONFERENCE_MAP.put("Sacramento Kings", "Western");
        TEAM_CONFERENCE_MAP.put("San Antonio Spurs", "Western");
        TEAM_CONFERENCE_MAP.put("Utah Jazz", "Western");
    }

    /**
     * Initialize team name aliases for flexible matching
     * Handles variations in team names found on different pages
     */
    private static void initializeTeamAliases() {
        // Common aliases and variations
        TEAM_NAME_ALIASES.put("Lakers", "Los Angeles Lakers");
        TEAM_NAME_ALIASES.put("Clippers", "LA Clippers");
        TEAM_NAME_ALIASES.put("Warriors", "Golden State Warriors");
        TEAM_NAME_ALIASES.put("Celtics", "Boston Celtics");
        TEAM_NAME_ALIASES.put("Heat", "Miami Heat");
        TEAM_NAME_ALIASES.put("Bulls", "Chicago Bulls");
        TEAM_NAME_ALIASES.put("Knicks", "New York Knicks");
        TEAM_NAME_ALIASES.put("Nets", "Brooklyn Nets");
        TEAM_NAME_ALIASES.put("76ers", "Philadelphia 76ers");
        TEAM_NAME_ALIASES.put("Sixers", "Philadelphia 76ers");
        TEAM_NAME_ALIASES.put("Mavs", "Dallas Mavericks");
        TEAM_NAME_ALIASES.put("Spurs", "San Antonio Spurs");
        TEAM_NAME_ALIASES.put("Thunder", "Oklahoma City Thunder");
        TEAM_NAME_ALIASES.put("Blazers", "Portland Trail Blazers");
        TEAM_NAME_ALIASES.put("Kings", "Sacramento Kings");
        TEAM_NAME_ALIASES.put("Suns", "Phoenix Suns");
        TEAM_NAME_ALIASES.put("Jazz", "Utah Jazz");
        TEAM_NAME_ALIASES.put("Nuggets", "Denver Nuggets");
        TEAM_NAME_ALIASES.put("Rockets", "Houston Rockets");
        TEAM_NAME_ALIASES.put("Grizzlies", "Memphis Grizzlies");
        TEAM_NAME_ALIASES.put("Pelicans", "New Orleans Pelicans");
        TEAM_NAME_ALIASES.put("Timberwolves", "Minnesota Timberwolves");
        TEAM_NAME_ALIASES.put("Hawks", "Atlanta Hawks");
        TEAM_NAME_ALIASES.put("Hornets", "Charlotte Hornets");
        TEAM_NAME_ALIASES.put("Cavaliers", "Cleveland Cavaliers");
        TEAM_NAME_ALIASES.put("Cavs", "Cleveland Cavaliers");
        TEAM_NAME_ALIASES.put("Pistons", "Detroit Pistons");
        TEAM_NAME_ALIASES.put("Pacers", "Indiana Pacers");
        TEAM_NAME_ALIASES.put("Bucks", "Milwaukee Bucks");
        TEAM_NAME_ALIASES.put("Magic", "Orlando Magic");
        TEAM_NAME_ALIASES.put("Raptors", "Toronto Raptors");
        TEAM_NAME_ALIASES.put("Wizards", "Washington Wizards");
    }

    /**
     * Initialize team abbreviations mapping
     */
    private static void initializeTeamAbbreviations() {
        // Eastern Conference abbreviations
        TEAM_ABBREVIATIONS.put("ATL", "Atlanta Hawks");
        TEAM_ABBREVIATIONS.put("BOS", "Boston Celtics");
        TEAM_ABBREVIATIONS.put("BKN", "Brooklyn Nets");
        TEAM_ABBREVIATIONS.put("CHA", "Charlotte Hornets");
        TEAM_ABBREVIATIONS.put("CHI", "Chicago Bulls");
        TEAM_ABBREVIATIONS.put("CLE", "Cleveland Cavaliers");
        TEAM_ABBREVIATIONS.put("DET", "Detroit Pistons");
        TEAM_ABBREVIATIONS.put("IND", "Indiana Pacers");
        TEAM_ABBREVIATIONS.put("MIA", "Miami Heat");
        TEAM_ABBREVIATIONS.put("MIL", "Milwaukee Bucks");
        TEAM_ABBREVIATIONS.put("NYK", "New York Knicks");
        TEAM_ABBREVIATIONS.put("ORL", "Orlando Magic");
        TEAM_ABBREVIATIONS.put("PHI", "Philadelphia 76ers");
        TEAM_ABBREVIATIONS.put("TOR", "Toronto Raptors");
        TEAM_ABBREVIATIONS.put("WAS", "Washington Wizards");

        // Western Conference abbreviations
        TEAM_ABBREVIATIONS.put("DAL", "Dallas Mavericks");
        TEAM_ABBREVIATIONS.put("DEN", "Denver Nuggets");
        TEAM_ABBREVIATIONS.put("GSW", "Golden State Warriors");
        TEAM_ABBREVIATIONS.put("HOU", "Houston Rockets");
        TEAM_ABBREVIATIONS.put("LAC", "LA Clippers");
        TEAM_ABBREVIATIONS.put("LAL", "Los Angeles Lakers");
        TEAM_ABBREVIATIONS.put("MEM", "Memphis Grizzlies");
        TEAM_ABBREVIATIONS.put("MIN", "Minnesota Timberwolves");
        TEAM_ABBREVIATIONS.put("NOP", "New Orleans Pelicans");
        TEAM_ABBREVIATIONS.put("OKC", "Oklahoma City Thunder");
        TEAM_ABBREVIATIONS.put("PHX", "Phoenix Suns");
        TEAM_ABBREVIATIONS.put("POR", "Portland Trail Blazers");
        TEAM_ABBREVIATIONS.put("SAC", "Sacramento Kings");
        TEAM_ABBREVIATIONS.put("SAS", "San Antonio Spurs");
        TEAM_ABBREVIATIONS.put("UTA", "Utah Jazz");
    }

    /**
     * Determine conference based on team name with intelligent matching
     * @param teamName Full team name, partial name, alias, or abbreviation
     * @return Conference name (Eastern/Western)
     */
    public static String determineConference(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            System.err.println("Warning: Empty team name provided");
            return "Unknown";
        }

        String cleanTeamName = teamName.trim();

        // Step 1: Try exact match
        if (TEAM_CONFERENCE_MAP.containsKey(cleanTeamName)) {
            return TEAM_CONFERENCE_MAP.get(cleanTeamName);
        }

        // Step 2: Try abbreviation match
        String upperTeamName = cleanTeamName.toUpperCase();
        if (TEAM_ABBREVIATIONS.containsKey(upperTeamName)) {
            String fullTeamName = TEAM_ABBREVIATIONS.get(upperTeamName);
            return TEAM_CONFERENCE_MAP.get(fullTeamName);
        }

        // Step 3: Try alias match
        for (Map.Entry<String, String> alias : TEAM_NAME_ALIASES.entrySet()) {
            if (cleanTeamName.equalsIgnoreCase(alias.getKey())) {
                String fullTeamName = alias.getValue();
                return TEAM_CONFERENCE_MAP.get(fullTeamName);
            }
        }

        // Step 4: Try partial matching (case-insensitive)
        for (Map.Entry<String, String> entry : TEAM_CONFERENCE_MAP.entrySet()) {
            String fullTeamName = entry.getKey();
            String conference = entry.getValue();

            // Check if team name contains any part of the full team name
            if (fullTeamName.toLowerCase().contains(cleanTeamName.toLowerCase()) ||
                    cleanTeamName.toLowerCase().contains(fullTeamName.toLowerCase())) {
                return conference;
            }

            // Check for city name or team name matches
            String[] teamParts = fullTeamName.split(" ");
            for (String part : teamParts) {
                if (part.toLowerCase().equals(cleanTeamName.toLowerCase())) {
                    return conference;
                }
            }
        }

        // Step 5: Try fuzzy matching with aliases
        for (Map.Entry<String, String> alias : TEAM_NAME_ALIASES.entrySet()) {
            if (cleanTeamName.toLowerCase().contains(alias.getKey().toLowerCase()) ||
                    alias.getKey().toLowerCase().contains(cleanTeamName.toLowerCase())) {
                String fullTeamName = alias.getValue();
                return TEAM_CONFERENCE_MAP.get(fullTeamName);
            }
        }

        // Default fallback
        System.err.println("Warning: Could not determine conference for team: " + teamName);
        return "Unknown";
    }

}