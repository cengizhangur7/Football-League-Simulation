package estu.ceng.Interface;

import estu.ceng.MainClasses.Team;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamStatsTable {

    // Takım istatistiklerini tablo halinde döndüren metod
    public static String generateTeamStatsTable(List<Team> teams) {

        Collections.sort(teams, Comparator.comparingDouble(Team::getTeamStrength).reversed());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "Team Name", "Goalkeeping Str.", "Defense Str.", "Midfield Str.", "Offense Str.", "Fan Str.", "Overall Str."));
        sb.append("-------------------------------------------------------------------------------------------------------------\n");

        for (Team team : teams) {
            sb.append(String.format("%-20s %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f %-15.2f\n",
                    team.getName(),
                    team.getGoalkeepingStrength(),
                    team.getDefenseStrength(),
                    team.getMidfieldStrength(),
                    team.getOffenseStrength(),
                    team.getAverageFanStrength(),
                    team.getTeamStrength()));
        }

        return sb.toString();
    }
}
