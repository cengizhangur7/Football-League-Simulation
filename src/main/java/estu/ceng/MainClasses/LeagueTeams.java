package estu.ceng.MainClasses;

import java.util.Arrays;
import java.util.List;

public class LeagueTeams {
    // Şampiyonlar Ligi'ndeki gerçek takımların listesini döndürür
    public static List<String> getTeams() {
        return Arrays.asList(
                "Real Madrid", "Barcelona", "Manchester United", "Bayern Munich",
                "Juventus", "Paris Saint-Germain", "Chelsea", "Liverpool",
                "Manchester City", "Atletico Madrid", "Borussia Dortmund", "Inter Milan",
                "AC Milan", "Ajax", "Porto", "Benfica", "Sevilla", "Lyon",
                "Napoli", "Roma", "Tottenham Hotspur", "RB Leipzig", "Lazio",
                "Shakhtar Donetsk", "Zenit St. Petersburg", "Galatasaray", "Olympiacos",
                "Club Brugge", "PSV Eindhoven", "Celtic", "Rangers", "Red Bull Salzburg"
        );
    }
}
