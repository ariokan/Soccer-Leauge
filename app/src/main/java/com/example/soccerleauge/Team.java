package com.example.soccerleauge;

public class Team {

    private String name;
    private int matches;
    private int goal_diff;
    private int goals_scored;
    private int goals_conceded;
    private int lost;
    private int drawn;
    private int won;
    private int teamid;
    private int rank;
    private int points;

    public Team(String name, int rank, int points, int matches, int goal_diff, int goals_scored, int goals_conceded, int lost, int drawn, int won, int teamid) {
        this.name = name;
        this.rank = rank;
        this.points = points;
        this.matches = matches;
        this.goal_diff = goal_diff;
        this.goals_scored = goals_scored;
        this.goals_conceded = goals_conceded;
        this.lost = lost;
        this.drawn = drawn;
        this.won = won;
        this.teamid = teamid;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public void setGoal_diff(int goal_diff) {
        this.goal_diff = goal_diff;
    }

    public void setGoals_scored(int goals_scored) {
        this.goals_scored = goals_scored;
    }

    public void setGoals_conceded(int goals_conceded) {
        this.goals_conceded = goals_conceded;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }
    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int getPoints() {
        return points;
    }

    public int getMatches() {
        return matches;
    }

    public int getGoal_diff() {
        return goal_diff;
    }

    public int getGoals_scored() {
        return goals_scored;
    }

    public int getGoals_conceded() {
        return goals_conceded;
    }

    public int getLost() {
        return lost;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getWon() {
        return won;
    }

    public int getTeamid() {
        return teamid;
    }
}
