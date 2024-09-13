package estu.ceng.Attributes;

import estu.ceng.Interface.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PhysicalAttributes implements Attributes {
    private int acceleration; // Hızlanma
    private int agility; // Çeviklik
    private int balance; // Denge
    private int jumpingReach; // Sıçrama
    private int naturalFitness; // Doğal kondisyon
    private int pace; // Hız
    private int stamina; // Dayanıklılık
    private int strength; // Güç

    private static final int MAX_ATTRIBUTE_VALUE = 20; // Maksimum yetenek değeri

    public PhysicalAttributes() {
        Random rand = new Random();
        this.acceleration = rand.nextInt(16) + 5;
        this.agility = rand.nextInt(16) + 5;
        this.balance = rand.nextInt(16) + 5;
        this.jumpingReach = rand.nextInt(16) + 5;
        this.naturalFitness = rand.nextInt(16) + 5;
        this.pace = rand.nextInt(16) + 5;
        this.stamina = rand.nextInt(16) + 5;
        this.strength = rand.nextInt(16) + 5;
    }

    // Yeteneklerin ortalamasını döndürür
    public double getAverage() {
        return (acceleration + agility + balance + jumpingReach + naturalFitness + pace + stamina + strength) / 8.0;
    }

    // Yeteneklerin haritasını döndürür
    public Map<String, Integer> getAttributes() {
        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("Acceleration", acceleration);
        attributes.put("Agility", agility);
        attributes.put("Balance", balance);
        attributes.put("Jumping Reach", jumpingReach);
        attributes.put("Natural Fitness", naturalFitness);
        attributes.put("Pace", pace);
        attributes.put("Stamina", stamina);
        attributes.put("Strength", strength);
        return attributes;
    }

    // Belirli bir yeteneği artıran method
    public void setAttribute(String attribute, int increaseValue) {
        switch (attribute) {
            case "Acceleration":
                this.acceleration = Math.min(20, this.acceleration + increaseValue);
                break;
            case "Agility":
                this.agility = Math.min(20, this.agility + increaseValue);
                break;
            case "Balance":
                this.balance = Math.min(20, this.balance + increaseValue);
                break;
            case "Jumping Reach":
                this.jumpingReach = Math.min(20, this.jumpingReach + increaseValue);
                break;
            case "Natural Fitness":
                this.naturalFitness = Math.min(20, this.naturalFitness + increaseValue);
                break;
            case "Pace":
                this.pace = Math.min(20, this.pace + increaseValue);
                break;
            case "Stamina":
                this.stamina = Math.min(20, this.stamina + increaseValue);
                break;
            case "Strength":
                this.strength = Math.min(20, this.strength + increaseValue);
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
