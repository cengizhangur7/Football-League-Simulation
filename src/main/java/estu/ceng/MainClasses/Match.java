package estu.ceng.MainClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Match {
    private Team homeTeam; // Ev sahibi takım
    private Team awayTeam; // Deplasman takımı
    private int homeGoals; // Ev sahibi takımın attığı gol sayısı
    private int awayGoals; // Deplasman takımının attığı gol sayısı
    private static List<Integer> homeFanStrengthPool; // Ev sahipleri için kullanılacak fan güçleri
    private static List<Integer> awayFanStrengthPool; // Deplasman takımları için kullanılacak fan güçleri
    private LocalDateTime matchDateTime; // Maçın tarihi ve saati

    // Constructor - Tarih ve saat ekliyoruz
    public Match(Team homeTeam, Team awayTeam, LocalDateTime matchDateTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDateTime = matchDateTime;
    }

    // Maç detaylarını gösteren metot
    public void displayMatchDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        System.out.println("Match: " + homeTeam.getName() + " vs " + awayTeam.getName() + " on " + matchDateTime.format(formatter));
    }

    // Haftalık fan güç havuzunu sıfırlayan metod
    public static void resetUsedFanStrengths() {
        homeFanStrengthPool = new ArrayList<>();
        awayFanStrengthPool = new ArrayList<>();

        // 11-20 aralığında ev sahibi fan güçlerini oluştur
        for (int i = 10; i <= 20; i++) {
            homeFanStrengthPool.add(i);
        }

        // 0-10 aralığında deplasman fan güçlerini oluştur
        for (int i = 0; i <= 10; i++) {
            awayFanStrengthPool.add(i);
        }

        // Rastgele karıştır, böylece her hafta farklı güçler atanır
        Collections.shuffle(homeFanStrengthPool);
        Collections.shuffle(awayFanStrengthPool);
    }

    // Ev sahibi ve deplasman için fan güçlerini atayan metod
    public int generateFanStrength(boolean isHome) {
        if (isHome) {
            if (homeFanStrengthPool.isEmpty()) {
                throw new IndexOutOfBoundsException("Home fan strength pool is empty!");
            }
            return homeFanStrengthPool.remove(0); // Ev sahibi fan güçlerinden sıradaki birini al
        } else {
            if (awayFanStrengthPool.isEmpty()) {
                throw new IndexOutOfBoundsException("Away fan strength pool is empty!");
            }
            return awayFanStrengthPool.remove(0); // Deplasman fan güçlerinden sıradaki birini al
        }
    }

    // Maçları simüle eden metod
    public void simulate() {
        Random rand = new Random();

        int homeFanStrength = homeTeam.getLastHomeFanStrength();
        double homeAdvantage = 1.1 + (homeFanStrength / 100.0); // Ev sahibi takımın taraftar avantajı
        double homeStrength = homeTeam.getOffenseStrength() * homeAdvantage; // Ev sahibi takımın hücum gücü
        double awayStrength = awayTeam.getOffenseStrength(); // Deplasman takımının hücum gücü

        int homeAttacks = rand.nextInt((int) homeStrength) + 1; // Ev sahibi takımın yapacağı hücum sayısı
        int awayAttacks = rand.nextInt((int) awayStrength) + 1; // Deplasman takımının yapacağı hücum sayısı

        double homeGoalkeepingStrength = homeTeam.getGoalkeepingStrength(); // Ev sahibi takımın kalecilik gücü
        double awayGoalkeepingStrength = awayTeam.getGoalkeepingStrength(); // Deplasman takımının kalecilik gücü

        // Ev sahibi takımın attığı golleri hesapla, deplasman takımının kalecilik gücünü dikkate al
        homeGoals = generateGoals(homeAttacks, homeTeam.getOffenseStrength(), awayTeam.getDefenseStrength(), rand, awayGoalkeepingStrength);

        // Deplasman takımının attığı golleri hesapla, ev sahibi takımının kalecilik gücünü dikkate al
        awayGoals = generateGoals(awayAttacks, awayTeam.getOffenseStrength(), homeTeam.getDefenseStrength(), rand, homeGoalkeepingStrength);

        // Maç sonuçlarını günceller
        homeTeam.addGoalsFor(homeGoals);
        homeTeam.addGoalsAgainst(awayGoals);
        awayTeam.addGoalsFor(awayGoals);
        awayTeam.addGoalsAgainst(homeGoals);

        if (homeGoals > awayGoals) {
            homeTeam.addPoints(3); // Ev sahibi takım kazanırsa 3 puan ekler
            homeTeam.addWin(); // Ev sahibi takımın galibiyet sayısını artırır
            homeTeam.addHomeWin(); // Ev galibiyeti eklenir
            awayTeam.addLoss(); // Deplasman takımının mağlubiyet sayısını artırır
            awayTeam.addAwayLoss(); // Deplasman mağlubiyeti eklenir
        } else if (awayGoals > homeGoals) {
            awayTeam.addPoints(3); // Deplasman takımı kazanırsa 3 puan ekler
            awayTeam.addWin(); // Deplasman takımının galibiyet sayısını artırır
            awayTeam.addAwayWin(); // Deplasman galibiyeti eklenir
            homeTeam.addLoss(); // Ev sahibi takımın mağlubiyet sayısını artırır
            homeTeam.addHomeLoss(); // Ev mağlubiyeti eklenir
        } else {
            homeTeam.addPoints(1); // Beraberlik durumunda her iki takıma 1 puan ekler
            awayTeam.addPoints(1);
            homeTeam.addDraw(); // Ev sahibi takımın beraberlik sayısını artırır
            awayTeam.addDraw(); // Deplasman takımının beraberlik sayısını artırır
        }
    }

    // Gol sayılarını belirleyen metod
    private int generateGoals(int attacks, double attackingStrength, double defendingStrength, Random rand, double goalkeepingStrength) {
        int goals = 0;

        // Başarılı olabilecek atakların sayısını hesaplar
        double potentialSuccessfulAttacks = (attackingStrength - defendingStrength);

        // Negatif değerleri önlemek için minimum bir olasılık ayarla
        if (potentialSuccessfulAttacks < 0) {
            // Eğer hücum gücü savunma gücünden düşükse, bu durumu dengelemek için
            // potansiyel başarılı atak sayısına küçük bir pozitif değer ekliyoruz
            potentialSuccessfulAttacks = Math.max(1, attacks * 0.1); // %10'luk bir minimum başarı şansı ekler
        }

        // Potansiyel başarılı atak sayısını bulmak için rastgele bir faktör kullanarak toplam atak sayısını ölçekler
        int successfulAttacks = (int) Math.min(attacks, potentialSuccessfulAttacks);

        // Kalecilik gücüne göre gol olma ihtimalini hesaplar
        double goalProbability = Math.max(0.1, 1.0 - (goalkeepingStrength / 20.0));
        for (int i = 0; i < successfulAttacks; i++) {
            if (rand.nextDouble() < goalProbability) {  // Kalecilik gücüne göre gol olma şansı
                goals++;
            }
        }

        return goals;
    }

    // Ev sahibi takım getter metodu
    public Team getHomeTeam() {
        return homeTeam;
    }

    // Deplasman takım getter metodu
    public Team getAwayTeam() {
        return awayTeam;
    }

    // Ev sahibi takımın gol sayısını döndüren metod
    public int getHomeGoals() {
        return homeGoals;
    }

    // Deplasman takımının gol sayısını döndüren metod
    public int getAwayGoals() {
        return awayGoals;
    }

    // Maç tarih ve saatini döndüren metod
    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }
}
