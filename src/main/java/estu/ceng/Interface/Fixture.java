package estu.ceng.Interface;

import estu.ceng.MainClasses.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fixture {
    private List<Team> teams; // Ligdeki takımların listesi
    private List<List<Match>> allMatches; // Tüm maçların listesi (hafta bazında)

    public Fixture(List<Team> teams) {
        this.teams = teams;
        this.allMatches = new ArrayList<>();
        generateFixture(); // Fikstürü oluşturur
    }

    // Fikstürü oluşturur
    private void generateFixture() {
        int numTeams = teams.size();
        boolean odd = (numTeams % 2 != 0); // Takım sayısının tek olup olmadığını kontrol eder
        List<Team> tempTeams = new ArrayList<>(teams);

        if (odd) {
            tempTeams.add(null); // Eğer takım sayısı tekse bye ekle
            numTeams++;
        }

        // İlk yarıyı ve ikinci yarıyı oluşturur
        List<Team> firstHalf = new ArrayList<>(tempTeams.subList(0, numTeams / 2));
        List<Team> secondHalf = new ArrayList<>(tempTeams.subList(numTeams / 2, numTeams));
        Collections.reverse(secondHalf); // İkinci yarıyı ters çevirir

        for (int round = 0; round < numTeams - 1; round++) {
            List<Match> weeklyMatches = new ArrayList<>(); // Haftalık maçları tutar
            for (int i = 0; i < firstHalf.size(); i++) {
                Team home = firstHalf.get(i);
                Team away = secondHalf.get(i);
                if (home != null && away != null) {
                    weeklyMatches.add(new Match(home, away)); // Haftalık maçları ekler
                }
            }
            allMatches.add(weeklyMatches); // Tüm maçlara haftalık maçları ekler

            // Takımları rotasyona tabi tut
            secondHalf.add(1, firstHalf.remove(0)); // İlk takım her hafta değişecek şekilde yer değiştir
            firstHalf.add(firstHalf.size(), secondHalf.remove(secondHalf.size() - 1)); // Diğer takımları rotasyona tabi tut
        }

        // İkinci yarı için ters ev sahibi-deplasman maçlarını ekle
        List<List<Match>> secondHalfMatches = new ArrayList<>();
        for (List<Match> week : allMatches) {
            List<Match> reverseWeek = new ArrayList<>();
            for (Match match : week) {
                reverseWeek.add(new Match(match.getAwayTeam(), match.getHomeTeam())); // Ev sahibi ve deplasmanı değiştirerek ekler
            }
            secondHalfMatches.add(reverseWeek);
        }
        allMatches.addAll(secondHalfMatches); // İkinci yarıyı tüm maçlara ekler
    }

    // Maçları simüle eder
    public void simulateMatches() {
        int week = 1;

        String boldStart = "\033[1m";
        String boldEnd = "\033[0m";

        for (List<Match> weeklyMatches : allMatches) {
            System.out.println();
            printLine(50);
            System.out.println(boldStart + "Week " + week + " Fixture:" + boldEnd);

            Match.resetUsedFanStrengths();

            for (Match match : weeklyMatches) {
                try {
                    int homeFanStrength = match.generateFanStrength(true);
                    int awayFanStrength = match.generateFanStrength(false);
                    match.getHomeTeam().addFanStrength(homeFanStrength);
                    match.getAwayTeam().addFanStrength(awayFanStrength);
                    System.out.println(match.getHomeTeam().getName() + "(Fan Strength:" + match.getHomeTeam().getFanStrength() + ")" + " vs " + match.getAwayTeam().getName() + "(Fan Strength:" +  match.getAwayTeam().getFanStrength() + ")");
                } catch (Exception e) {
                    System.out.println("Error generating fan strengths: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("\n" + boldStart + "Week " + week + " Results:" + boldEnd);
            for (Match match : weeklyMatches) {
                try {
                    match.simulate();
                    System.out.println(match.getHomeTeam().getName() + " " + match.getHomeGoals() + " - " + match.getAwayGoals() + " " + match.getAwayTeam().getName());

                } catch (Exception e) {
                    System.out.println("Error during match simulation: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println();
            printLeagueTable(week, boldStart,boldEnd);
            printLine(50);
            week++;
        }
    }

    private void printLeagueTable(int week, String boldStart, String boldEnd) {
        System.out.println("\n" + boldStart+ "Week " + week + " Leaderboard");
        System.out.printf(boldStart + "%-6s %-20s %-5s %-5s %-5s %-5s %-5s %-5s %-5s" +boldEnd + "\n", "Güç", "İsim", "P", "A", "AG", "YG", "G", "B", "M");
        Collections.sort(teams, (a, b) -> {
            if (b.getPoints() != a.getPoints()) return b.getPoints() - a.getPoints();
            if ((b.getGoalsFor() - b.getGoalsAgainst()) != (a.getGoalsFor() - a.getGoalsAgainst())) return (b.getGoalsFor() - b.getGoalsAgainst()) - (a.getGoalsFor() - a.getGoalsAgainst());
            if (b.getGoalsFor() != a.getGoalsFor()) return b.getGoalsFor() - a.getGoalsFor();
            return b.getWins() - a.getWins();
        });
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            double overallStrength = team.getTeamStrength();  // Takımın genel gücünü al
            System.out.printf("%-6.2f %-20s %-5d %-5d %-5d %-5d %-5d %-5d %-5d\n",
                    overallStrength,
                    team.getName(),
                    team.getPoints(),
                    team.getGoalDifference(),
                    team.getGoalsFor(),
                    team.getGoalsAgainst(),
                    team.getWins(),
                    team.getDraws(),
                    team.getLosses()
            );
        }
    }






    // Bir çizgi çizer
    private void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
