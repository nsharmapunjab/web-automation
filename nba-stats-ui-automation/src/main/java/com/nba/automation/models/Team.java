package com.nba.automation.models;

/**
 * Team model class to represent NBA team data
 * Contains team information and statistics
 */
public class Team {
    private String name;
    private String conference;
    private int wins;
    private int losses;
    private double winPercentage;

    /**
     * Default constructor
     */
    public Team() {}

    /**
     * Constructor with all fields
     * @param name Team name
     * @param conference Team conference (Eastern/Western)
     * @param wins Number of wins
     * @param losses Number of losses
     * @param winPercentage Win percentage
     */
    public Team(String name, String conference, int wins, int losses, double winPercentage) {
        this.name = name;
        this.conference = conference;
        this.wins = wins;
        this.losses = losses;
        this.winPercentage = winPercentage;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getConference() { return conference; }

    public int getWins() { return wins; }

    @Override
    public String toString() {
        return String.format("Team{name='%s', conference='%s', wins=%d, losses=%d, winPercentage=%.3f}",
                name, conference, wins, losses, winPercentage);
    }
}