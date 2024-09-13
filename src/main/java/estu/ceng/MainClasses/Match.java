package estu.ceng.MainClasses;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Match {
    private Team homeTeam; // Ev sahibi takım
    private Team awayTeam; // Deplasman takımı
    private int homeGoals; // Ev sahibi takımın attığı gol sayısı
    private int awayGoals; // Deplasman takımının attığı gol sayısı
    private static Set<Integer> usedFanStrengths; // Her hafta için kullanılan fan güçlerini takip etmek için

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    // Haftalık fan güçlerini sıfırlayan bir metod
    public static void resetUsedFanStrengths() {
        usedFanStrengths = new HashSet<>();
    }

    // Maçları simüle eden metod
    public void simulate() {
        Random rand = new Random();
        double homeAdvantage = 1.1 + (homeTeam.getFanStrength() / 100.0); // Ev sahibi takımın taraftar avantajı
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

        /**Hücum gücü defans gücünden düşük olmasına rağmen gol atan takımları kontrol eder ve yazdırır
        if (homeTeam.getOffenseStrength() < awayTeam.getDefenseStrength() && homeGoals > 0) {
            System.out.println("Home team " + homeTeam.getName() + " scored despite lower offense strength.");
        }

        if (awayTeam.getOffenseStrength() < homeTeam.getDefenseStrength() && awayGoals > 0) {
            System.out.println("Away team " + awayTeam.getName() + " scored despite lower offense strength.");
        }*/

        // Maç sonuçlarını günceller
        homeTeam.addGoalsFor(homeGoals);
        homeTeam.addGoalsAgainst(awayGoals);
        awayTeam.addGoalsFor(awayGoals);
        awayTeam.addGoalsAgainst(homeGoals);

        if (homeGoals > awayGoals) {
            homeTeam.addPoints(3); // Ev sahibi takım kazanırsa 3 puan ekler
            homeTeam.addWin(); // Ev sahibi takımın galibiyet sayısını artırır
            awayTeam.addLoss(); // Deplasman takımının mağlubiyet sayısını artırır
        } else if (awayGoals > homeGoals) {
            awayTeam.addPoints(3); // Deplasman takımı kazanırsa 3 puan ekler
            awayTeam.addWin(); // Deplasman takımının galibiyet sayısını artırır
            homeTeam.addLoss(); // Ev sahibi takımın mağlubiyet sayısını artırır
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
        // Kalecilik gücü arttıkça gol olma ihtimali düşer
        double goalProbability = Math.max(0.1, 1.0 - (goalkeepingStrength / 20.0));
        for (int i = 0; i < successfulAttacks; i++) {
            if (rand.nextDouble() < goalProbability) {  // Kalecilik gücüne göre gol olma şansı
                goals++;
            }
        }

        return goals;
    }


    // Fan gücünü oluşturan metod
    public int generateFanStrength(boolean isHome) {
        Random rand = new Random();
        int fanStrength;
        int maxAttempts = 100; // Sonsuz döngüyü önlemek için maksimum deneme sayısı
        int attempts = 0;

        if (isHome) {
            do {
                fanStrength = rand.nextInt(11) + 10; // 10-20 arasında rastgele bir sayı
                attempts++;
                if (attempts >= maxAttempts) {
                    throw new RuntimeException("Unable to generate a unique fan strength for home team.");
                }
            } while (usedFanStrengths.contains(fanStrength));
        } else {
            do {
                fanStrength = rand.nextInt(10) + 1; // 1-10 arasında rastgele bir sayı
                attempts++;
                if (attempts >= maxAttempts) {
                    throw new RuntimeException("Unable to generate a unique fan strength for away team.");
                }
            } while (usedFanStrengths.contains(fanStrength));
        }
        usedFanStrengths.add(fanStrength);
        return fanStrength;
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
}
