package estu.ceng;

import estu.ceng.MainClasses.Fixture;
import estu.ceng.MainClasses.TeamStatsTable;
import estu.ceng.MainClasses.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int NUMBER_OF_TEAMS = 18; // Ligde yer alacak takım sayısı
    private static List<Team> selectedTeams = new ArrayList<>(); // Seçilen takımların listesi

    public static void main(String[] args) {
        initializeTeams(); // Takımları başlatır ve rastgele seçer

        Fixture fixture = new Fixture(selectedTeams); // Fikstürü oluşturur
        fixture.simulateMatches(); // Maçları simüle eder

        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMainMenu(); // Ana menüyü gösterir
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showPlayerMenu(scanner); // Oyuncu menüsünü gösterir
                    break;
                case 2:
                    showTeamStats(); // Takım istatistiklerini gösterir
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Geçersiz seçim mesajı
            }
        }
    }

    // Lig için takımları başlatır ve rastgele seçer
    private static void initializeTeams() {
        List<String> allTeams = LeagueTeams.getTeams(); // Tüm takımları alır
        Collections.shuffle(allTeams); // Takım listesini karıştırır
        for (int i = 0; i < NUMBER_OF_TEAMS; i++) {
            selectedTeams.add(new Team(allTeams.get(i))); // Seçilen takımları listeye ekler
        }
    }

    // Ana menüyü gösterir
    private static void showMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Player");
        System.out.println("2. Teams Stats");  // Yeni seçenek eklendi
        System.out.print("Enter your choice: ");
    }

    // Oyuncu menüsünü gösterir
    private static void showPlayerMenu(Scanner scanner) {
        System.out.println("Select a team:");
        for (int i = 0; i < selectedTeams.size(); i++) {
            System.out.println((i + 1) + ". " + selectedTeams.get(i).getName());
        }
        System.out.print("Enter team number: ");
        int teamChoice = scanner.nextInt();
        if (teamChoice > 0 && teamChoice <= selectedTeams.size()) {
            Team selectedTeam = selectedTeams.get(teamChoice - 1);
            List<Player> players = selectedTeam.getPlayers();
            for (Player player : players) {
                player.printAttributes(); // Oyuncu özelliklerini yazdırır
            }
        } else {
            System.out.println("Invalid team choice."); // Geçersiz takım seçimi
        }
    }

    // Takım menüsünü gösterir
    private static void showTeamMenu(Scanner scanner) {
        System.out.println("Select a team:");
        for (int i = 0; i < selectedTeams.size(); i++) {
            System.out.println((i + 1) + ". " + selectedTeams.get(i).getName());
        }
        System.out.print("Enter team number: ");
        int teamChoice = scanner.nextInt();
        if (teamChoice > 0 && teamChoice <= selectedTeams.size()) {
            Team selectedTeam = selectedTeams.get(teamChoice - 1);
            selectedTeam.printTeamStrengths(); // Takım güçlerini yazdırır
        } else {
            System.out.println("Invalid team choice."); // Geçersiz takım seçimi
        }
    }

    // Takım istatistiklerini gösterir
    private static void showTeamStats() {
        String table = TeamStatsTable.generateTeamStatsTable(selectedTeams);
        System.out.println(table); // Takım istatistik tablosunu yazdırır
    }
}
