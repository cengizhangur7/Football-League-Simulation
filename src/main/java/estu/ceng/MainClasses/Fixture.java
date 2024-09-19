package estu.ceng.MainClasses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fixture {
    private List<Team> teams; // Ligdeki takımların listesi
    private List<List<Match>> allMatches; // Tüm maçların listesi (hafta bazında)
    private LocalDate startDate; // Ligin başlangıç tarihi
    private static final LocalTime[] matchTimes = {LocalTime.of(15, 0), LocalTime.of(18, 0), LocalTime.of(20, 45)}; // Maç saatleri
    private static final int MATCHES_PER_WEEK = 9; // Haftalık maç sayısı

    public Fixture(List<Team> teams) {
        this.teams = teams;
        this.allMatches = new ArrayList<>();
        this.startDate = LocalDate.of(2024, 9, 1); // Ligin başlangıç tarihi
        generateFixture(); // Fikstürü oluşturur
    }

    // Fikstürü oluşturur
    private void generateFixture() {
        int numTeams = teams.size();
        boolean odd = (numTeams % 2 != 0);
        List<Team> tempTeams = new ArrayList<>(teams);

        if (odd) {
            tempTeams.add(null);
            numTeams++;
        }

        // İlk yarıyı oluşturur
        for (int round = 0; round < numTeams - 1; round++) {
            List<Match> weeklyMatches = new ArrayList<>();
            LocalDate currentWeekFriday = startDate.plusWeeks(round);

            // Cuma, Cumartesi, Pazar günlerine yayarak maçları planla
            for (int i = 0; i < numTeams / 2; i++) {
                LocalDate matchDay = currentWeekFriday.plusDays(i / 3); // Cuma: 0, Cumartesi: 1, Pazar: 2

                Team home = tempTeams.get(i);
                Team away = tempTeams.get(numTeams - i - 1);
                if (home != null && away != null) {
                    LocalTime matchTime = matchTimes[i % matchTimes.length];
                    LocalDateTime matchDateTime = LocalDateTime.of(matchDay, matchTime);

                    weeklyMatches.add(new Match(home, away, matchDateTime));
                }
            }
            allMatches.add(weeklyMatches);

            Team lastTeam = tempTeams.remove(numTeams - 1);
            tempTeams.add(1, lastTeam);
        }

        // İkinci yarıyı oluştur
        List<List<Match>> secondHalfMatches = new ArrayList<>(); // Yeni bir liste oluştur
        for (List<Match> week : allMatches) {
            List<Match> reverseWeek = new ArrayList<>();
            for (Match match : week) {
                LocalDateTime matchDateTime = match.getMatchDateTime().plusWeeks(numTeams - 1);
                reverseWeek.add(new Match(match.getAwayTeam(), match.getHomeTeam(), matchDateTime));
            }
            secondHalfMatches.add(reverseWeek); // İkinci yarıyı ayrı listeye ekle
        }
        allMatches.addAll(secondHalfMatches); // Son olarak, ikinci yarıyı allMatches'e ekle
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

            Match.resetUsedFanStrengths(); // Her hafta için fan güçleri sıfırlanır

            for (int i = 0; i < MATCHES_PER_WEEK; i++) { // Sadece 9 maç için fan gücü üretilecek
                Match match = weeklyMatches.get(i);
                try {
                    int homeFanStrength = match.generateFanStrength(true);
                    int awayFanStrength = match.generateFanStrength(false);
                    match.getHomeTeam().addHomeFanStrength(homeFanStrength); // Ev maçları için fan gücü ekleniyor
                    match.getAwayTeam().addAwayFanStrength(awayFanStrength); // Deplasman maçları için fan gücü ekleniyor

                    System.out.println(match.getHomeTeam().getName() + " vs " +
                            match.getAwayTeam().getName() + " --- " +
                            match.getMatchDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")));
                } catch (Exception e) {
                    System.out.println("Error generating fan strengths: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println("\n" + boldStart + "Week " + week + " Results:" + boldEnd);
            for (int i = 0; i < MATCHES_PER_WEEK; i++) {
                Match match = weeklyMatches.get(i);
                try {
                    match.simulate(); // Maçı simüle eder
                    System.out.println(match.getHomeTeam().getName() + " " + match.getHomeGoals() + " - " + match.getAwayGoals() + " " + match.getAwayTeam().getName());
                } catch (Exception e) {
                    System.out.println("Error during match simulation: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            System.out.println();
            printLeagueTable(week, boldStart, boldEnd); // Haftalık lig tablosunu yazdır
            printLine(50);
            week++;
        }
    }

    // Liderlik tablosunu yazdırır
    private void printLeagueTable(int week, String boldStart, String boldEnd) {
        System.out.println("\n" + boldStart + "Week " + week + " Leaderboard");
        System.out.printf(boldStart + "%-6s %-20s %-5s %-5s %-5s %-5s %-5s %-5s %-5s" + boldEnd + "\n", "Güç", "İsim", "P", "A", "AG", "YG", "G", "B", "M");
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
