package com.nba.automation.models;

/**
 * Player model class to represent NBA player data
 * Contains player information and statistics
 */
public class Player {
    private String name;
    private String team;
    private double pointsPerGame;
    private double assistsPerGame;
    private double reboundsPerGame;

    /**
     * Default constructor
     */
    public Player() {}

    /**
     * Constructor with all fields
     * @param name Player name
     * @param team Player's team
     * @param pointsPerGame Points per game
     * @param assistsPerGame Assists per game
     * @param reboundsPerGame Rebounds per game
     */
    public Player(String name, String team, double pointsPerGame, double assistsPerGame, double reboundsPerGame) {
        this.name = name;
        this.team = team;
        this.pointsPerGame = pointsPerGame;
        this.assistsPerGame = assistsPerGame;
        this.reboundsPerGame = reboundsPerGame;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public double getPointsPerGame() { return pointsPerGame; }
    public void setPointsPerGame(double pointsPerGame) { this.pointsPerGame = pointsPerGame; }

    public double getAssistsPerGame() { return assistsPerGame; }
    public void setAssistsPerGame(double assistsPerGame) { this.assistsPerGame = assistsPerGame; }

    public double getReboundsPerGame() { return reboundsPerGame; }
    public void setReboundsPerGame(double reboundsPerGame) { this.reboundsPerGame = reboundsPerGame; }

    @Override
    public String toString() {
        return String.format("Player{name='%s', team='%s', ppg=%.1f, apg=%.1f, rpg=%.1f}",
                name, team, pointsPerGame, assistsPerGame, reboundsPerGame);
    }
}