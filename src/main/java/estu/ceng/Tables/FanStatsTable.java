package estu.ceng.Tables;

import estu.ceng.MainClasses.Team;
import java.util.List;

public class FanStatsTable {
    private List<Team> teams; // Takımların listesi

    public FanStatsTable(List<Team> teams) {
        this.teams = teams; // Takımları al
    }

    // Takım ismini kısaltma metodu
    private String abbreviateTeamName(String teamName) {
        String[] nameParts = teamName.split(" "); // İsimleri boşlukla ayır
        if (nameParts.length > 1) {
            String firstInitial = nameParts[0].substring(0, 1) + "."; // İlk kelimenin ilk harfi ve nokta
            String secondPart = nameParts[1]; // İkinci kelime
            return firstInitial + " " + secondPart; // Kısaltılmış isim
        }
        return teamName; // Eğer bir kelimeden oluşuyorsa orijinal ismi döndür
    }

    public void displayFanStatistics() {
        System.out.print(String.format("%-9s", "Maç")); // Sütun başlığı için boşluk
        for (Team team : teams) {
            System.out.print(String.format("%-12s", abbreviateTeamName(team.getName()))); // Kısaltılmış takım isimleri
        }
        System.out.println();

        // Ev fan güçleri
        for (int i = 0; i < 17; i++) { // 17 ev maçı varsayıyoruz
            System.out.print(String.format("%-12s", "Home " + (i + 1)));
            for (Team team : teams) {
                if (i < team.getHomeFanStrengths().size()) {
                    System.out.print(String.format("%-12d", team.getHomeFanStrengths().get(i))); // Ev fan güçlerini yazdır
                } else {
                    System.out.print(String.format("%-12s", "-")); // Veri eksikse yer tutucu
                }
            }
            System.out.println();
        }

        // Deplasman fan güçleri
        for (int i = 0; i < 17; i++) { // 17 deplasman maçı varsayıyoruz
            System.out.print(String.format("%-12s", "Away " + (i + 1)));
            for (Team team : teams) {
                if (i < team.getAwayFanStrengths().size()) {
                    System.out.print(String.format("%-12d", team.getAwayFanStrengths().get(i))); // Deplasman fan güçlerini yazdır
                } else {
                    System.out.print(String.format("%-12s", "-")); // Veri eksikse yer tutucu
                }
            }
            System.out.println();
        }
    }
}
