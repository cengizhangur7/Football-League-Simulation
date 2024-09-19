package estu.ceng.MainClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {
    private String name; // Takım adı
    private List<Player> players; // Oyuncuların listesi
    private int points; // Takımın puanı
    private int goalsFor; // Takımın attığı gol sayısı
    private int goalsAgainst; // Takımın yediği gol sayısı
    private int wins; // Takımın kazandığı maç sayısı
    private int draws; // Takımın berabere kaldığı maç sayısı
    private int losses; // Takımın kaybettiği maç sayısı

    // Yeni liste yapıları - Ev ve deplasman fan güçleri ayrı ayrı izleniyor
    private List<Integer> homeFanStrengths; // Takımın ev maçlarındaki fan güçleri
    private List<Integer> awayFanStrengths; // Takımın deplasman maçlarındaki fan güçleri

    private int homeWins; // Evde kazanılan maç sayısı
    private int awayWins; // Deplasmanda kazanılan maç sayısı
    private int homeLosses; // Evde kaybedilen maç sayısı
    private int awayLosses; // Deplasmanda kaybedilen maç sayısı

    private double offenseRandomAdjustment; // Hücum gücüne eklenen rastgele değer
    private double defenseRandomAdjustment; // Defans gücüne eklenen rastgele değer
    private double midfieldRandomAdjustment; // Orta saha gücüne eklenen rastgele değer
    private double goalkeepingRandomAdjustment; // Kalecilik gücüne eklenen rastgele değer

    private static final int MAX_PLAYERS_GOALKEEPER = 3; // Maksimum kaleci sayısı
    private static final int MAX_PLAYERS_DEFENDER = 10; // Maksimum defans oyuncusu sayısı
    private static final int MAX_PLAYERS_MIDFIELDER = 6; // Maksimum orta saha oyuncusu sayısı
    private static final int MAX_PLAYERS_ATTACKER = 6; // Maksimum forvet oyuncusu sayısı

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.points = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.homeFanStrengths = new ArrayList<>();
        this.awayFanStrengths = new ArrayList<>();
        this.homeWins = 0;
        this.awayWins = 0;
        this.homeLosses = 0;
        this.awayLosses = 0;

        // Rastgele değerleri bir kez hesaplar ve saklar
        Random rand = new Random();
        this.offenseRandomAdjustment = 1 + rand.nextInt(30); // 1 ile 30 arasında rastgele bir sayı
        this.defenseRandomAdjustment = 1 + rand.nextInt(30); // 1 ile 30 arasında rastgele bir sayı
        this.midfieldRandomAdjustment = 1 + rand.nextInt(30); // 1 ile 30 arasında rastgele bir sayı
        this.goalkeepingRandomAdjustment = 1 + rand.nextInt(30); // 1 ile 30 arasında rastgele bir sayı
        generatePlayers(); // Takım için oyuncuları oluşturur
    }

    // Pozisyona göre oyuncuları oluşturur
    private void generatePlayers() {
        generatePlayersByPosition("Goalkeeper", MAX_PLAYERS_GOALKEEPER);
        generatePlayersByPosition("Defender", MAX_PLAYERS_DEFENDER);
        generatePlayersByPosition("Midfielder", MAX_PLAYERS_MIDFIELDER);
        generatePlayersByPosition("Attacker", MAX_PLAYERS_ATTACKER);
    }

    // Belirli bir pozisyon için oyuncu üretir
    private void generatePlayersByPosition(String position, int count) {
        for (int i = 0; i < count; i++) {
            players.add(new Player(position.substring(0, 2).toUpperCase() + i, position));
        }
    }

    // Takımın genel gücünü hesaplar
    public double getTeamStrength() {
        double defenseStrength = getDefenseStrength();
        double midfieldStrength = getMidfieldStrength();
        double offenseStrength = getOffenseStrength();
        double goalkeepingStrength = getGoalkeepingStrength();

        return (defenseStrength + midfieldStrength + offenseStrength + goalkeepingStrength) / 4.0;
    }

    // Defans gücünü hesaplar
    public double getDefenseStrength() {
        double baseDefenseStrength = players.stream()
                .filter(p -> p.getPosition().equals("Defender"))
                .mapToDouble(Player::getStrength)
                .average().orElse(0);
        return baseDefenseStrength + defenseRandomAdjustment;
    }

    // Orta saha gücünü hesaplar
    public double getMidfieldStrength() {
        double baseMidfieldStrength = players.stream()
                .filter(p -> p.getPosition().equals("Midfielder"))
                .mapToDouble(Player::getStrength)
                .average().orElse(0);
        return  baseMidfieldStrength + midfieldRandomAdjustment;
    }

    // Hücum gücünü hesaplar
    public double getOffenseStrength() {
        double baseOffenseStrength = players.stream()
                .filter(p -> p.getPosition().equals("Attacker"))
                .mapToDouble(Player::getStrength)
                .average().orElse(0);
        return baseOffenseStrength + offenseRandomAdjustment;
    }

    // Kalecilik gücünü hesaplar
    public double getGoalkeepingStrength() {
        double baseGoalkeepingStrength = players.stream()
                .filter(p -> p.getPosition().equals("Goalkeeper"))
                .mapToDouble(Player::getStrength)
                .average().orElse(0);
        return baseGoalkeepingStrength + goalkeepingRandomAdjustment;
    }

    // Getter methodları
    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    // Takıma puan ekler
    public void addPoints(int points) {
        this.points += points;
    }

    // Takımın attığı gol sayısını ekler
    public void addGoalsFor(int goals) {
        this.goalsFor += goals;
    }

    // Takımın yediği gol sayısını ekler
    public void addGoalsAgainst(int goals) {
        this.goalsAgainst += goals;
    }

    // Takıma galibiyet ekler
    public void addWin() {
        this.wins += 1;
    }

    // Takıma beraberlik ekler
    public void addDraw() {
        this.draws += 1;
    }

    // Takıma mağlubiyet ekler
    public void addLoss() {
        this.losses += 1;
    }

    // Takımın averajını hesaplar
    public int getGoalDifference() {
        return this.goalsFor - this.goalsAgainst;
    }

    // Ev maçlarına ait taraftar gücünü ekler
    public void addHomeFanStrength(int strength) {
        this.homeFanStrengths.add(strength);
    }

    // Deplasman maçlarına ait taraftar gücünü ekler
    public void addAwayFanStrength(int strength) {
        this.awayFanStrengths.add(strength);
    }

    // Takımın evde aldığı taraftar güçlerini döndüren metot
    public List<Integer> getHomeFanStrengths() {
        return homeFanStrengths;
    }

    // Takımın deplasmanda aldığı taraftar güçlerini döndüren metot
    public List<Integer> getAwayFanStrengths() {
        return awayFanStrengths;
    }

    // Ev galibiyetini arttıran metod
    public void addHomeWin() {
        this.homeWins++;
    }

    // Deplasman galibiyetini arttıran metod
    public void addAwayWin() {
        this.awayWins++;
    }

    // Ev mağlubiyetini arttıran metod
    public void addHomeLoss() {
        this.homeLosses++;
    }

    // Deplasman mağlubiyetini arttıran metod
    public void addAwayLoss() {
        this.awayLosses++;
    }

    // Getter metodlar
    public int getHomeWins() {
        return homeWins;
    }

    public int getAwayWins() {
        return awayWins;
    }

    public int getHomeLosses() {
        return homeLosses;
    }

    public int getAwayLosses() {
        return awayLosses;
    }

    // Takımın gücünü yazdırır
    public void printTeamStrengths() {
        System.out.println("Team: " + name);
        System.out.printf("Overall Strength: %.2f\n", getTeamStrength());
        System.out.printf("Defensive Strength: %.2f\n", getDefenseStrength());
        System.out.printf("Midfield Strength: %.2f\n", getMidfieldStrength());
        System.out.printf("Offensive Strength: %.2f\n", getOffenseStrength());
        System.out.printf("Goalkeeping Strength: %.2f\n", getGoalkeepingStrength());
        System.out.printf("Home Fan Strength: %.2f\n", getAverageHomeFanStrength());
        System.out.printf("Away Fan Strength: %.2f\n", getAverageAwayFanStrength());
        System.out.println();
    }

    // Takımın ortalama ev taraftar gücünü hesaplar
    public double getAverageHomeFanStrength() {
        return homeFanStrengths.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    // Takımın ortalama deplasman taraftar gücünü hesaplar
    public double getAverageAwayFanStrength() {
        return awayFanStrengths.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    public double getAverageFanStrength() {
        double homeAverage = homeFanStrengths.stream().mapToInt(Integer::intValue).average().orElse(0);
        double awayAverage = awayFanStrengths.stream().mapToInt(Integer::intValue).average().orElse(0);
        return (homeAverage + awayAverage) / 2.0; // Ev ve deplasman ortalamasını döndürür
    }

    public int getLastHomeFanStrength() {
        if (homeFanStrengths.isEmpty()) {
            return 0; // Eğer ev maçları yoksa 0 döndür
        }
        return homeFanStrengths.get(homeFanStrengths.size() - 1); // En son eklenen ev taraftar gücünü döndür
    }


}
