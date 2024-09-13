package estu.ceng.Attributes;

import estu.ceng.Interface.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MentalAttributes implements Attributes {
    private int aggression; // Agresyon
    private int anticipation; // Öngörü
    private int bravery; // Cesaret
    private int composure; // Soğukkanlılık
    private int concentration; // Konsantrasyon
    private int decisions; // Karar verme
    private int determination; // Kararlılık
    private int flair; // Yaratıcılık
    private int leadership; // Liderlik
    private int offTheBall; // Topsuz oyun
    private int positioning; // Pozisyon alma
    private int teamwork; // Takım çalışması
    private int vision; // Vizyon
    private int workRate; // Çalışma oranı

    private static final int MAX_ATTRIBUTE_VALUE = 20; // Maksimum yetenek değeri

    public MentalAttributes() {
        Random rand = new Random();
        this.aggression = rand.nextInt(16) + 5;
        this.anticipation = rand.nextInt(16) + 5;
        this.bravery = rand.nextInt(16) + 5;
        this.composure = rand.nextInt(16) + 5;
        this.concentration = rand.nextInt(16) + 5;
        this.decisions = rand.nextInt(16) + 5;
        this.determination = rand.nextInt(16) + 5;
        this.flair = rand.nextInt(16) + 5;
        this.leadership = rand.nextInt(16) + 5;
        this.offTheBall = rand.nextInt(16) + 5;
        this.positioning = rand.nextInt(16) + 5;
        this.teamwork = rand.nextInt(16) + 5;
        this.vision = rand.nextInt(16) + 5;
        this.workRate = rand.nextInt(16) + 5;
    }

    // Yeteneklerin ortalamasını döndürür
    public double getAverage() {
        return (aggression + anticipation + bravery + composure + concentration + decisions + determination + flair +
                leadership + offTheBall + positioning + teamwork + vision + workRate) / 14.0;
    }

    // Yeteneklerin haritasını döndürür
    public Map<String, Integer> getAttributes() {
        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("Aggression", aggression);
        attributes.put("Anticipation", anticipation);
        attributes.put("Bravery", bravery);
        attributes.put("Composure", composure);
        attributes.put("Concentration", concentration);
        attributes.put("Decisions", decisions);
        attributes.put("Determination", determination);
        attributes.put("Flair", flair);
        attributes.put("Leadership", leadership);
        attributes.put("Off the Ball", offTheBall);
        attributes.put("Positioning", positioning);
        attributes.put("Teamwork", teamwork);
        attributes.put("Vision", vision);
        attributes.put("Work Rate", workRate);
        return attributes;
    }

    // Belirli bir yeteneği artıran method
    public void setAttribute(String attribute, int increaseValue) {
        switch (attribute) {
            case "Aggression":
                this.aggression = Math.min(20, this.aggression + increaseValue);
                break;
            case "Anticipation":
                this.anticipation = Math.min(20, this.anticipation + increaseValue);
                break;
            case "Bravery":
                this.bravery = Math.min(20, this.bravery + increaseValue);
                break;
            case "Composure":
                this.composure = Math.min(20, this.composure + increaseValue);
                break;
            case "Concentration":
                this.concentration = Math.min(20, this.concentration + increaseValue);
                break;
            case "Decisions":
                this.decisions = Math.min(20, this.decisions + increaseValue);
                break;
            case "Determination":
                this.determination = Math.min(20, this.determination + increaseValue);
                break;
            case "Flair":
                this.flair = Math.min(20, this.flair + increaseValue);
                break;
            case "Leadership":
                this.leadership = Math.min(20, this.leadership + increaseValue);
                break;
            case "Off the Ball":
                this.offTheBall = Math.min(20, this.offTheBall + increaseValue);
                break;
            case "Positioning":
                this.positioning = Math.min(20, this.positioning + increaseValue);
                break;
            case "Teamwork":
                this.teamwork = Math.min(20, this.teamwork + increaseValue);
                break;
            case "Vision":
                this.vision = Math.min(20, this.vision + increaseValue);
                break;
            case "Work Rate":
                this.workRate = Math.min(20, this.workRate + increaseValue);
                break;
        }
    }

    // Yetenekleri yazdıran method
    public void printAttributes() {
        Map<String, Integer> attributes = getAttributes();
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            System.out.println(" " + entry.getKey().toLowerCase() + ": " + entry.getValue());
        }
    }
}
