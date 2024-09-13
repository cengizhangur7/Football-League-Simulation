package estu.ceng.Attributes;

import estu.ceng.Interface.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TechnicalAttributes implements Attributes {
    private int corners; // Korner kullanma
    private int crossing; // Ortalama
    private int dribbling; // Dribling
    private int finishing; // Bitiricilik
    private int firstTouch; // İlk dokunuş
    private int freeKickTaking; // Serbest vuruş
    private int heading; // Kafa vuruşu
    private int longShots; // Uzun şutlar
    private int marking; // Adam markajı
    private int passing; // Pas verme
    private int penaltyTaking; // Penaltı kullanma
    private int tackling; // Top çalma
    private int technique; // Teknik
    private int throwing; // Top fırlatma


    public TechnicalAttributes() {
        Random rand = new Random();
        this.corners = rand.nextInt(16) + 5;
        this.crossing = rand.nextInt(16) + 5;
        this.dribbling = rand.nextInt(16) + 5;
        this.finishing = rand.nextInt(16) + 5;
        this.firstTouch = rand.nextInt(16) + 5;
        this.freeKickTaking = rand.nextInt(16) + 5;
        this.heading = rand.nextInt(16) + 5;
        this.longShots = rand.nextInt(16) + 5;
        this.marking = rand.nextInt(16) + 5;
        this.passing = rand.nextInt(16) + 5;
        this.penaltyTaking = rand.nextInt(16) + 5;
        this.tackling = rand.nextInt(16) + 5;
        this.technique = rand.nextInt(16) + 5;
        this.throwing = rand.nextInt(16) + 5;
    }

    // Yeteneklerin ortalamasını döndürür
    public double getAverage() {
        return (corners + crossing + dribbling + finishing + firstTouch + freeKickTaking + heading + longShots +
                marking + passing + penaltyTaking + tackling + technique + throwing) / 14.0;
    }

    // Yeteneklerin haritasını döndürür
    public Map<String, Integer> getAttributes() {
        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("Corners", corners);
        attributes.put("Crossing", crossing);
        attributes.put("Dribbling", dribbling);
        attributes.put("Finishing", finishing);
        attributes.put("First Touch", firstTouch);
        attributes.put("Free Kick Taking", freeKickTaking);
        attributes.put("Heading", heading);
        attributes.put("Long Shots", longShots);
        attributes.put("Marking", marking);
        attributes.put("Passing", passing);
        attributes.put("Penalty Taking", penaltyTaking);
        attributes.put("Tackling", tackling);
        attributes.put("Technique", technique);
        attributes.put("Throwing", throwing);
        return attributes;
    }

    // Belirli bir yeteneği artıran method
    public void setAttribute(String attribute, int increaseValue) {
        switch (attribute) {
            case "Corners":
                this.corners = Math.min(20, this.corners + increaseValue);
                break;
            case "Crossing":
                this.crossing = Math.min(20, this.crossing + increaseValue);
                break;
            case "Dribbling":
                this.dribbling = Math.min(20, this.dribbling + increaseValue);
                break;
            case "Finishing":
                this.finishing = Math.min(20, this.finishing + increaseValue);
                break;
            case "First Touch":
                this.firstTouch = Math.min(20, this.firstTouch + increaseValue);
                break;
            case "Free Kick Taking":
                this.freeKickTaking = Math.min(20, this.freeKickTaking + increaseValue);
                break;
            case "Heading":
                this.heading = Math.min(20, this.heading + increaseValue);
                break;
            case "Long Shots":
                this.longShots = Math.min(20, this.longShots + increaseValue);
                break;
            case "Marking":
                this.marking = Math.min(20, this.marking + increaseValue);
                break;
            case "Passing":
                this.passing = Math.min(20, this.passing + increaseValue);
                break;
            case "Penalty Taking":
                this.penaltyTaking = Math.min(20, this.penaltyTaking + increaseValue);
                break;
            case "Tackling":
                this.tackling = Math.min(20, this.tackling + increaseValue);
                break;
            case "Technique":
                this.technique = Math.min(20, this.technique + increaseValue);
                break;
            case "Throwing":
                this.throwing = Math.min(20, this.throwing + increaseValue);
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
