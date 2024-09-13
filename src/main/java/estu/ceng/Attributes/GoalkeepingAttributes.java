package estu.ceng.Attributes;

import estu.ceng.Interface.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GoalkeepingAttributes implements Attributes {
    private int aerialReach; // Hava hakimiyeti
    private int commandOfArea; // Alan hakimiyeti
    private int communication; // İletişim
    private int eccentricity; // Eksantriklik
    private int handling; // Top kontrolü
    private int kicking; // Şut gücü
    private int oneOnOnes; // Bire bir pozisyonlar
    private int reflexes; // Refleksler
    private int rushingOut; // Açık oyun
    private int throwing; // Top atma

    private static final int MAX_ATTRIBUTE_VALUE = 20; // Maksimum yetenek değeri

    public GoalkeepingAttributes() {
        Random rand = new Random();
        this.aerialReach = rand.nextInt(16) + 5;
        this.commandOfArea = rand.nextInt(16) + 5;
        this.communication = rand.nextInt(16) + 5;
        this.eccentricity = rand.nextInt(16) + 5;
        this.handling = rand.nextInt(16) + 5;
        this.kicking = rand.nextInt(16) + 5;
        this.oneOnOnes = rand.nextInt(16) + 5;
        this.reflexes = rand.nextInt(16) + 5;
        this.rushingOut = rand.nextInt(16) + 5;
        this.throwing = rand.nextInt(16) + 5;
    }

    // Yeteneklerin ortalamasını döndürür
    public double getAverage() {
        return (aerialReach + commandOfArea + communication + eccentricity + handling + kicking + oneOnOnes + reflexes +
                rushingOut + throwing) / 10.0;
    }

    // Yeteneklerin haritasını döndürür
    public Map<String, Integer> getAttributes() {
        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("Aerial Reach", aerialReach);
        attributes.put("Command of Area", commandOfArea);
        attributes.put("Communication", communication);
        attributes.put("Eccentricity", eccentricity);
        attributes.put("Handling", handling);
        attributes.put("Kicking", kicking);
        attributes.put("One on Ones", oneOnOnes);
        attributes.put("Reflexes", reflexes);
        attributes.put("Rushing Out", rushingOut);
        attributes.put("Throwing", throwing);
        return attributes;
    }

    // Belirli bir yeteneği artıran method
    public void setAttribute(String attribute, int increaseValue) {
        switch (attribute) {
            case "Aerial Reach":
                this.aerialReach = Math.min(20, this.aerialReach + increaseValue);
                break;
            case "Command of Area":
                this.commandOfArea = Math.min(20, this.commandOfArea + increaseValue);
                break;
            case "Communication":
                this.communication = Math.min(20, this.communication + increaseValue);
                break;
            case "Eccentricity":
                this.eccentricity = Math.min(20, this.eccentricity + increaseValue);
                break;
            case "Handling":
                this.handling = Math.min(20, this.handling + increaseValue);
                break;
            case "Kicking":
                this.kicking = Math.min(20, this.kicking + increaseValue);
                break;
            case "One on Ones":
                this.oneOnOnes = Math.min(20, this.oneOnOnes + increaseValue);
                break;
            case "Reflexes":
                this.reflexes = Math.min(20, this.reflexes + increaseValue);
                break;
            case "Rushing Out":
                this.rushingOut = Math.min(20, this.rushingOut + increaseValue);
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
