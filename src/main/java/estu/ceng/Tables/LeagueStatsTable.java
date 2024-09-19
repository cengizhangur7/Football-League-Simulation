package estu.ceng.Tables;

import estu.ceng.MainClasses.Team;

import java.util.List;

public class LeagueStatsTable {

    private List<Team> teams;


    public LeagueStatsTable(List<Team> teams) {
        this.teams = teams;
    }

    // En çok gol atan takımı bulur
    public Team getTeamWithMostGoalsScored() {
        Team topScoringTeam = null;
        int maxGoals = 0;
        for (Team team : teams) {
            if (team.getGoalsFor() > maxGoals) {
                maxGoals = team.getGoalsFor();
                topScoringTeam = team;
            }
        }
        return topScoringTeam;
    }

    // En çok gol yiyen takımı bulur
    public Team getTeamWithMostGoalsConceded() {
        Team mostConcededTeam = null;
        int maxConceded = 0;
        for (Team team : teams) {
            if (team.getGoalsAgainst() > maxConceded) {
                maxConceded = team.getGoalsAgainst();
                mostConcededTeam = team;
            }
        }
        return mostConcededTeam;
    }

    // En yüksek averaja sahip takımı bulur
    public Team getTeamWithHighestGoalDifference() {
        Team bestGoalDifferenceTeam = null;
        int bestGoalDifference = Integer.MIN_VALUE;
        for (Team team : teams) {
            int goalDifference = team.getGoalDifference();
            if (goalDifference > bestGoalDifference) {
                bestGoalDifference = goalDifference;
                bestGoalDifferenceTeam = team;
            }
        }
        return bestGoalDifferenceTeam;
    }

    // Kendi evinde en çok maç kazanan takımı bulur
    public Team getTeamWithMostHomeWins() {
        Team mostHomeWinsTeam = null;
        int maxHomeWins = 0;
        for (Team team : teams) {
            if (team.getHomeWins() > maxHomeWins) {
                maxHomeWins = team.getHomeWins();
                mostHomeWinsTeam = team;
            }
        }
        return mostHomeWinsTeam;
    }

    // Deplasmanda en çok maç kazanan takımı bulur
    public Team getTeamWithMostAwayWins() {
        Team mostAwayWinsTeam = null;
        int maxAwayWins = 0;
        for (Team team : teams) {
            if (team.getAwayWins() > maxAwayWins) {
                maxAwayWins = team.getAwayWins();
                mostAwayWinsTeam = team;
            }
        }
        return mostAwayWinsTeam;
    }

    // Kendi evinde en çok yenilen takımı bulur
    public Team getTeamWithMostHomeLosses() {
        Team mostHomeLossesTeam = null;
        int maxHomeLosses = 0;
        for (Team team : teams) {
            if (team.getHomeLosses() > maxHomeLosses) {
                maxHomeLosses = team.getHomeLosses();
                mostHomeLossesTeam = team;
            }
        }
        return mostHomeLossesTeam;
    }

    // Deplasmanda en çok yenilen takımı bulur
    public Team getTeamWithMostAwayLosses() {
        Team mostAwayLossesTeam = null;
        int maxAwayLosses = 0;
        for (Team team : teams) {
            if (team.getAwayLosses() > maxAwayLosses) {
                maxAwayLosses = team.getAwayLosses();
                mostAwayLossesTeam = team;
            }
        }
        return mostAwayLossesTeam;
    }

    // En çok galibiyet alan takımı bulur
    public Team getTeamWithMostWins() {
        Team mostWinsTeam = null;
        int maxWins = 0;
        for (Team team : teams) {
            if (team.getWins() > maxWins) {
                maxWins = team.getWins();
                mostWinsTeam = team;
            }
        }
        return mostWinsTeam;
    }

    // En çok mağlubiyet alan takımı bulur
    public Team getTeamWithMostLosses() {
        Team mostLossesTeam = null;
        int maxLosses = 0;
        for (Team team : teams) {
            if (team.getLosses() > maxLosses) {
                maxLosses = team.getLosses();
                mostLossesTeam = team;
            }
        }
        return mostLossesTeam;
    }

    // En çok berabere kalan takımı bulur
    public Team getTeamWithMostDraws() {
        Team mostDrawsTeam = null;
        int maxDraws = 0;
        for (Team team : teams) {
            if (team.getDraws() > maxDraws) {
                maxDraws = team.getDraws();
                mostDrawsTeam = team;
            }
        }
        return mostDrawsTeam;
    }

    // İstatistikleri görüntüleme metodu
    public void displayLeagueStats() {
        System.out.println("Team with Most Goals Scored: " + getTeamWithMostGoalsScored().getName());
        System.out.println("Team with Most Goals Conceded: " + getTeamWithMostGoalsConceded().getName());
        System.out.println("Team with Highest Goal Difference: " + getTeamWithHighestGoalDifference().getName());
        System.out.println("Team with Most Home Wins: " + getTeamWithMostHomeWins().getName());
        System.out.println("Team with Most Away Wins: " + getTeamWithMostAwayWins().getName());
        System.out.println("Team with Most Home Losses: " + getTeamWithMostHomeLosses().getName());
        System.out.println("Team with Most Away Losses: " + getTeamWithMostAwayLosses().getName());
        System.out.println("Team with Most Wins: " + getTeamWithMostWins().getName());
        System.out.println("Team with Most Losses: " + getTeamWithMostLosses().getName());
        System.out.println("Team with Most Draws: " + getTeamWithMostDraws().getName());
    }
}
